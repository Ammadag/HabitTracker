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
import com.example.habittracker.adapters.ViewPagerAdapter
import com.example.habittracker.constants.Constants
import com.example.habittracker.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!


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
        val fragmentList = arrayListOf(
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



        binding.fab.setOnClickListener {
            handleFabClick()
        }
    }

    private fun handleFabClick() {
        val viewPager = binding.viewPager2
        when (viewPager.currentItem) {
            0 -> viewPager.currentItem = 1
            1 -> viewPager.currentItem = 2
            2 -> viewPager.currentItem = 3
            3 -> {
                onBoardingFinalFab()

                findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            }
        }
    }

    private fun onBoardingFinalFab() {
        val sharedPref = requireActivity().getSharedPreferences(
            Constants.EXTRAS.ONBOARDING,
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putBoolean(Constants.KEYS.FABKEY, true)
        editor.apply()
    }
}





