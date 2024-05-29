package com.example.habittracker.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.adapters.StatsItemClick
import com.example.habittracker.adapters.StatsRVAdapter
import com.example.habittracker.databinding.FragmentStatsBinding
import com.example.habittracker.databinding.StatsdialogBinding
import com.example.habittracker.room.StatsRvInfo
import com.example.habittracker.viewmodel.StatsRoomVM
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var statRoomVM: StatsRoomVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        statRoomVM = ViewModelProvider(requireActivity())[StatsRoomVM::class.java]
        setUi()
        binding.fabStats.setOnClickListener {
            showAlertDialog(null)
        }
        return binding.root
    }

    private fun setUi() {
        binding.statsRv.layoutManager = LinearLayoutManager(requireContext())
        statRoomVM.getData().observe(viewLifecycleOwner) { dataList ->
            binding.statsRv.adapter = StatsRVAdapter(dataList, object : StatsItemClick {
                override fun onUpdate(userinfo: StatsRvInfo) {
                    showAlertDialog(userinfo)
                }

                override fun onDelete(id: Int) {
                    statRoomVM.deleteItem(id)
                }
            })

            if (dataList.isEmpty()) {
                binding.emptyList.visibility = View.VISIBLE
                binding.statsRv.visibility = View.GONE
            } else {
                binding.emptyList.visibility = View.GONE
                binding.statsRv.visibility = View.VISIBLE
            }
        }
    }

    private fun String.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

    private fun showAlertDialog(userinfo: StatsRvInfo?) {
        val dialog = Dialog(requireContext())
        val dialogBinding = StatsdialogBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
           650,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        // Set up the spinner
        val spinner: Spinner = dialogBinding.etTitle
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_array, 
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        if (userinfo != null) {
            val position = resources.getStringArray(R.array.spinner_array).indexOf(userinfo.title)
            if (position >= 0) {
                spinner.setSelection(position)
            }
            dialogBinding.etDistance.text = userinfo.distance.toEditable()
        }

        dialogBinding.btnAdd.setOnClickListener {
            val title = spinner.selectedItem?.toString()
            val distance = dialogBinding.etDistance.text
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

            val currentDate = dateFormat.format(calendar.time)
            val currentTime = timeFormat.format(calendar.time)
            val currentDay = dayFormat.format(calendar.time)

            if (title.isNullOrEmpty() || distance.isNullOrEmpty()) {
                dialogBinding.etDistance.error = "Please enter all fields"
                return@setOnClickListener
            }

            if (userinfo != null) {
                statRoomVM.updateData(StatsRvInfo(userinfo.id, title, distance.toString(),currentTime, currentDate, currentDay))
                dialog.dismiss()
            } else {
                statRoomVM.insertData(StatsRvInfo(0, title, distance.toString(),currentTime, currentDate, currentDay))
                dialog.dismiss()
            }
        }

        dialogBinding.cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }
}
