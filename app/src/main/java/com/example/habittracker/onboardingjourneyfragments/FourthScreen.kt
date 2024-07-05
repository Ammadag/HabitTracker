package com.example.habittracker.onboardingjourneyfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.constants.OnboardingFragment
import com.example.habittracker.databinding.FragmentFirstScreenBinding
import com.example.habittracker.databinding.FragmentFourthScreenBinding


class FourthScreen : Fragment(), OnboardingFragment {
    private var _binding: FragmentFourthScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getFragment(): Fragment = this

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


