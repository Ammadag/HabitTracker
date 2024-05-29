package com.example.habittracker.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cncoderx.wheelview.OnWheelChangedListener
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentBMIBinding

class BMIFragment : Fragment() {

    private var _binding: FragmentBMIBinding? = null
    private val binding get() = _binding!!
//    private lateinit var weightAdapter: WeightPickerAdapter
    private var gender = 'M'
    private var height = 1
    private var weight = 50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBMIBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationView()

        val titlesOfGender: List<String> = listOf("F", "O", "M")

        binding.genderWheelView.apply {
            titles = titlesOfGender
            elevation = 0f
            isFocusedByDefault = true
            isSelected = true
            focusedIndex = 2
        }
        binding.genderWheelView.selectListener = {
            gender = titlesOfGender[it][0]
        }

//        val snapHelper: SnapHelper = LinearSnapHelper()
//        snapHelper.attachToRecyclerView(binding.weightRecyclerBtn)
//
//        weightAdapter = WeightPickerAdapter(requireContext(), getData(), binding.weightRecyclerBtn)
//
//        binding.weightRecyclerBtn.defaultFocusHighlightEnabled = true
//        val pickerLayoutManager = PickerLayoutManager(requireActivity(), PickerLayoutManager.HORIZONTAL, false)
//        pickerLayoutManager.apply {
//            isChangeAlpha = true
//            scaleDownBy = 0.99f
//            scaleDownDistance = 0.8f
//            initialPrefetchItemCount = 3
//            isSmoothScrollbarEnabled = true
//            scrollToPosition(49)
//        }
//        binding.weightRecyclerBtn.apply {
//            layoutManager = pickerLayoutManager
//            adapter = weightAdapter
//            isSelected = true
//            requestFocus()
//            isFocusedByDefault = true
//        }

//        pickerLayoutManager.setOnScrollStopListener {
//            weight = Integer.parseInt((view as TextView).text.toString())
//        }

        // Height wheel
        binding.heightWheel.let { wheel ->
            wheel.defaultFocusHighlightEnabled = true
            wheel.onWheelChangedListener  = OnWheelChangedListener { mView, _, newIndex ->
                val text = mView.getItem(newIndex)
                height = Integer.parseInt(text.toString())
                Log.i("bmi","Height $height ")

            }
        }

        // Weight wheel
        binding.weightWheel.onWheelChangedListener =
            OnWheelChangedListener { tView, _, newIndex ->
                val text = tView.getItem(newIndex)
                weight = Integer.parseInt(text.toString())
                Log.i("bmi","weight $weight ")
            }
        binding.weightWheel.apply {
            defaultFocusHighlightEnabled = true
        }

        binding.startButton.setOnActiveListener {
            animationViewUp()
            binding.startButton.alpha = 0f
            Handler(Looper.getMainLooper()).postDelayed({
                val bundle = Bundle().apply {
                    putInt("height", height)
                    putInt("weight", weight)
                    putInt("gender", if (gender == 'M') 0 else 1)
                }
                findNavController().navigate(R.id.action_BMIFragment_to_resultFragment, bundle)
            }, 500)
        }
    }

//    private fun getData(): List<String> {
//        val data: MutableList<String> = ArrayList()
//        for (i in 0..150) {
//            data.add(i.toString())
//        }
//        return data
//    }

    private fun animationView() {
        binding.apply {
            bodyContainer.translationY = 150f
            footerContainer.translationY = 150f
            heightWheel.translationY = 150f
            weightWheel.translationX = 150f

            bodyContainer.alpha = 0f
            footerContainer.alpha = 0f
            heightWheel.alpha = 0f
            weightWheel.alpha = 0f

            bodyContainer.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(300).start()
            footerContainer.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(400).start()
            heightWheel.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(450).start()
            weightWheel.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(500).start()
        }
    }

    private fun animationViewUp() {
        binding.apply {
            textView.animate().translationY(0f).alpha(0f).setDuration(50).setStartDelay(0).start()
            bodyContainer.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(0).start()
            footerContainer.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(50).start()
            heightWheel.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(100).start()
            weightWheel.animate().translationX(-250f).alpha(0f).setDuration(500).setStartDelay(150).start()
        }
    }


}
