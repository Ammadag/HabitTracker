package com.example.habittracker.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.activity.MainActivity
import com.example.habittracker.adapters.OnItemClick
import com.example.habittracker.adapters.RVAdapter
import com.example.habittracker.databinding.DialogueBoxBinding
import com.example.habittracker.databinding.FragmentHomeBinding
import com.example.habittracker.room.RvInfo
import com.example.habittracker.viewmodel.HomeViewModel
import com.example.habittracker.viewmodel.RoomViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment(), SensorEventListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var roomVM: RoomViewModel
    private val calender: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("d", Locale.getDefault())
    private val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        roomVM = ViewModelProvider(requireActivity())[RoomViewModel::class.java]
        setupUI()
        registerSensors()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calenderDayAndDates()
        clickListeners()
        walkingSteps()
        cyclingDistance()

    }
    override fun onPause() {
        super.onPause()

        viewModel.unregisterSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                viewModel.handleAccelerometerEvent(it)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun showCalenderDialog() {

        val calendar = Calendar.getInstance()
        val todayInMillis = calendar.timeInMillis

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setSelection(todayInMillis)

        val datePicker = builder.build()
        datePicker.show(parentFragmentManager, datePicker.toString())
    }


    private fun cyclingDistance() {
        viewModel.currentCyclingDistance.observe(viewLifecycleOwner) { distance ->
            binding.cycleDistance.text = String.format("%.1f", distance) + " meters"
            val cyclingProgress = (distance / viewModel.maxCycling * 100).toInt()
            binding.pBarCycle.progress = cyclingProgress
        }
    }

    private fun walkingSteps() {
        viewModel.currentWalkingSteps.observe(viewLifecycleOwner) { steps ->
            binding.distanceWalking.text = "$steps Steps"
            val walkingProgress =
                (steps.toDouble() / viewModel.maxStepCount.toDouble() * 100).toInt()
            binding.pBarWalking.progress = walkingProgress
        }
    }

    private fun registerSensors() {
        viewModel.registerSensor(Sensor.TYPE_ACCELEROMETER)
    }


    private fun setupUI() {
        binding.homeRv.layoutManager = LinearLayoutManager(requireContext())
        roomVM.getData().observe(viewLifecycleOwner) { dataList ->


            binding.homeRv.adapter = RVAdapter(dataList, object : OnItemClick {
                override fun onUpdate(userinfo: RvInfo) {
                    showAlertDialog(userinfo)
                }


                override fun onDelete(id: Int) {
                    roomVM.deleteItem(id)
                }


            })
            if (dataList.isEmpty()) {

                binding.emptyRv.visibility = View.VISIBLE
                binding.homeRv.visibility = View.GONE


            } else {

                binding.emptyRv.visibility = View.GONE
                binding.homeRv.visibility = View.VISIBLE
            }

        }

    }





    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

    private fun showAlertDialog(userinfo: RvInfo?) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogueBoxBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
          630,
            ViewGroup.LayoutParams.WRAP_CONTENT,

        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
        if (userinfo != null) {
            dialogBinding.title.editText?.text = userinfo.title.toEditable()
            dialogBinding.desc.editText?.text = userinfo.desc.toEditable()
        }
        val title = dialogBinding.title.editText?.text
        val desc = dialogBinding.desc.editText?.text
        dialogBinding.btnAdd.setOnClickListener {
            if (userinfo != null) {
                roomVM.updateData(RvInfo(0, title.toString(), desc.toString()))
                dialog.dismiss()
            } else {
                roomVM.insertData(RvInfo(0, title.toString(), desc.toString()))
                dialog.dismiss()
            }
        }
        dialogBinding.cancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }

    private fun clickListeners() {
        binding.apply {
            toggleDrawer.setOnClickListener {
                (requireActivity() as MainActivity).openDrawer()

            }

            waterCard.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_waterDrinking)
            }
            fabHome.setOnClickListener {
                showAlertDialog(null)
            }
            btnPro.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_subscriptionFragment)
            }
            cardToday.setOnClickListener {
                showCalenderDialog()
            }
            bmi.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_BMIFragment)
            }
            walking.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_statsFragment)
            }
            cycling.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_statsFragment)
            }

        }
    }

    private fun calenderDayAndDates() {
        binding.apply {
            today.text = dayFormat.format(calender.time)
            dTday.text = dateFormat.format(calender.time)
            calender.add(Calendar.DATE, 1)

            d1.text = dayFormat.format(calender.time)
            dD1.text = dateFormat.format(calender.time)
            calender.add(Calendar.DATE, 1)

            d2.text = dayFormat.format(calender.time)
            dD2.text = dateFormat.format(calender.time)
            calender.add(Calendar.DATE, 1)

            d3.text = dayFormat.format(calender.time)
            dD3.text = dateFormat.format(calender.time)
            calender.add(Calendar.DATE, 1)
        }
    }


}
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                val position = viewHolder.adapterPosition
//                roomVM.deleteItem(position)
//                binding.homeRv.adapter?.notifyItemRemoved(position)
//
//            }
//
//        }
//        )
//
//    }
//        Snackbar.make(binding.root, "Item Deleted", Snackbar.LENGTH_LONG)
//            .setAction("Undo", View.OnClickListener {
//                roomVM.insertData(roomVM.undoDelete())
//                binding.homeRv.adapter?.notifyItemInserted(roomVM.undoDelete())
//            })
