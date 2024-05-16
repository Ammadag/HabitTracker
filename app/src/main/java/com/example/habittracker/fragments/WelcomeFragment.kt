package com.example.habittracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
        }
        return view
    }
}
