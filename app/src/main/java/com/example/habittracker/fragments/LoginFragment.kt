package com.example.habittracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentLoginBinding
import com.example.habittracker.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingOverlay: View
    private lateinit var authVM: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        authVM = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.pBarLogin
        loadingOverlay = binding.overley

        clickListeners()

    }

    private fun logInResult() {
        authVM.loginResult.observe(viewLifecycleOwner) { isSuccess ->

            if (isSuccess) {
                findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
                hideLoading()
            } else {
                Toast.makeText(requireActivity(),
                    getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        }
    }

    private fun clickListeners() {
        binding.apply {
            btnSignup.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btnLogin.setOnClickListener {

                val email = binding.inputemail.text.toString()
                val password = binding.inputpass.text.toString()
                authVM.loginUser(email, password)
                showLoading()
                logInResult()
            }

        }

    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        loadingOverlay.visibility = View.VISIBLE

    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        loadingOverlay.visibility = View.GONE

    }
}
