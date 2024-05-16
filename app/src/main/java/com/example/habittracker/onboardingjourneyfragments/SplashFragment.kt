package com.example.habittracker.onboardingjourneyfragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashFragment : Fragment() {

    private var   auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser: FirebaseUser? = auth.currentUser
            if (currentUser != null) {
                findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
            }
            else if(onBoardingFinalFab()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 2000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    private fun onBoardingFinalFab():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)

    }
}