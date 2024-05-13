package com.example.habittracker.onboardingjourneyfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentFourthScreenBinding

class FourthScreen : Fragment() {
    private var _binding: FragmentFourthScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFourthScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.finalFab.setOnClickListener {
            onBoardingFinalFab()
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }
        return view
    }
    private fun onBoardingFinalFab(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}
