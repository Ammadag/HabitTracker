package com.example.habittracker.onboardingjourneyfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.habittracker.R
import com.example.habittracker.adapters.ViewPagerAdapter
import com.example.habittracker.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private var clickCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            FourthScreen(),
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        viewPager.adapter = adapter


//        binding.fab.setOnClickListener {
//
//            clickCount++
//
//            when (clickCount % 4) {
//
//                0 -> {
//                    viewPager.currentItem = 1
//                }
//                1 -> {
//                    viewPager.currentItem = 2
//                }
//                2 -> {
//                    viewPager.currentItem = 3
//                }
//                3 -> {
//                    viewPager.currentItem = 4
//                }

            }
        }





