package com.example.habittracker.onboardingjourneyfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.viewpager2.widget.ViewPager2
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)
        binding.fab.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }

}