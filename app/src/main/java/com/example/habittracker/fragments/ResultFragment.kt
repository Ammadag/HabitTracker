package com.example.habittracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habittracker.databinding.FragmentResult2Binding

class ResultFragment : Fragment() {

    private var _binding: FragmentResult2Binding? = null
    private val binding get() = _binding!!
    private var weight: Int = 50
    private var height: Int = 150
    private var result: Double = 1.0
    private var gender: Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentResult2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            height = it.getInt("weight", 150)
            weight = it.getInt("height", 50)
            gender = it.getInt("gender", 0)
        }

        bmiCal()
        animationView()
        binding.reloadBtn.setOnClickListener {
            backPreviousPage()
        }

        binding.deleteBtn.setOnClickListener {
            backPreviousPage()
        }

    }

    private fun backPreviousPage() {
        parentFragmentManager.popBackStack()
    }

    private fun bmiCal() {
        val weightInPonds = weight *  0.3937
        val heightInInches = height * 2.204
        result = ((heightInInches / (weightInPonds * weightInPonds)) * 703)
        val message =
            if (result < 18) {
            "Underweight"
        } else if (result < 24.9) {
            "You Are Healthy"
        } else if (result < 29) {
            "Overweight"
        } else {
            "Obesity"
        }

        binding.resultText.text = result.toString()
        binding.bmiText.text = message

    }

    private fun animationView() {
        binding.apply {
            detailView.translationY = 150f
            detailView.alpha = 0f
            detailView.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(300)
                .start()

            reloadBtn.translationX = -150f
            reloadBtn.alpha = 0f
            reloadBtn.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(400)
                .start()

            shareBtn.translationX = 150f
            shareBtn.alpha = 0f
            shareBtn.animate().translationX(0f).alpha(1f).setDuration(500).setStartDelay(400)
                .start()

            deleteBtn.translationY = 150f
            deleteBtn.alpha = 0f
            deleteBtn.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(500)
                .start()
        }
    }

}
