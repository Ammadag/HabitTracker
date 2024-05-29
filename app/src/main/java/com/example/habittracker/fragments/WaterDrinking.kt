package com.example.habittracker.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.example.habittracker.adapters.OnItemClick
import com.example.habittracker.adapters.RVAdapter
import com.example.habittracker.databinding.DialogueBoxBinding
import com.example.habittracker.databinding.FragmentWaterDrinkingBinding
import com.example.habittracker.room.RvInfo
import com.example.habittracker.viewmodel.RoomViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate


class WaterDrinking: Fragment() {



    private var _binding: FragmentWaterDrinkingBinding? = null
    private val binding get() = _binding!!
    private lateinit var roomVM: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterDrinkingBinding.inflate(inflater, container, false)
        roomVM = ViewModelProvider(requireActivity())[RoomViewModel::class.java]
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charts()
        clickListeners()
      }

    private fun clickListeners() {
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigate(R.id.action_waterDrinking_to_homeFragment)
            }
            proV.setOnClickListener {
                findNavController().navigate(R.id.action_waterDrinking_to_subscriptionFragment)
            }
//            reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
//                if (isChecked) {
//                    Toast.makeText(requireContext(), "Water Reminder Enabled", Toast.LENGTH_SHORT).show()
//                    scheduleNotificationWorker()
//                } else {
//                    cancelNotificationWorker()
//                }


        }
    }

    private fun charts() {
        val chart = binding.chart
        val data = BarData()
        data.barWidth =5f
        data.addDataSet(getDataSet())
        chart.data = data
        chart.description.text = "My Chart"
        chart.animateXY(2000, 2000)
        chart.invalidate()
        chart.description.text = ""
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        chart.legend.isEnabled = false
        chart.setDrawValueAboveBar(false)
    }

    private fun getDataSet(): BarDataSet {
        val valueSet1 = arrayListOf(
            BarEntry(5f, 0f), // Day 1
            BarEntry(10f, 1f), // Day 2
            BarEntry(20f, 2f), // Day 3
            BarEntry(30f, 3f), // Day 4
            BarEntry(40f, 4f), // Day 5
            BarEntry(50f, 5f), // Day 6
            BarEntry(60f, 6f)  // Day 7
        )

        val barDataSet1 = BarDataSet(valueSet1, "Days").apply {
            color = Color.rgb(0, 155, 0)
            setColors(*ColorTemplate.COLORFUL_COLORS)
        }

        return barDataSet1
    }

    private fun setupUI() {
        binding.RV.layoutManager = LinearLayoutManager(requireContext())
        roomVM.getData().observe(viewLifecycleOwner) {
            binding.RV.adapter = RVAdapter(it, object : OnItemClick {
                override fun onUpdate(userinfo: RvInfo) {
                    showAlertDialog(userinfo)
                }

                override fun onDelete(id: Int) {
                    roomVM.deleteItem(id)
                }

            })
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
            ViewGroup.LayoutParams.MATCH_PARENT,
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
//    private fun scheduleNotificationWorker() {
//
//        Handler(Looper.getMainLooper())
//        val workRequest = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, TimeUnit.MINUTES)
//            .build()
//
//        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
//            "water_drinking_reminder",
//            ExistingPeriodicWorkPolicy.UPDATE,
//            workRequest
//        )
//    }
//
//    private fun cancelNotificationWorker() {
//        WorkManager.getInstance(requireContext()).cancelUniqueWork("water_drinking_reminder")
//    }


}



