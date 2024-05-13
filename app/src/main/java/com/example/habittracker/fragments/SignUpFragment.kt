package com.example.habittracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentLoginBinding
import com.example.habittracker.databinding.FragmentSignUpBinding
import com.example.habittracker.viewmodel.AuthViewModel


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingOverlay: View
    private lateinit var authVM: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        val view = binding.root

        authVM = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        progressBar = binding.pBarLogin
        loadingOverlay = binding.overley
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingUsername()
        signUpResults()
        ClickListeners()


    }

    private fun signUpResults() {

        authVM.signupResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                hideLoading()
                Toast.makeText(requireActivity(), "Registration Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            } else {
                Toast.makeText(requireActivity(), "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun ClickListeners() {
        binding.apply {
            btnLogIn.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
            btnRegister.setOnClickListener {
                showLoading()
                val email = binding.inputemail.text.toString()
                val password = binding.inputpass.text.toString()
                authVM.signUpUser(email, password)
            }
        }


    }

    private fun settingUsername() {
        sharedPreferences = requireActivity()
            .getSharedPreferences(
                "UserName", Context.MODE_PRIVATE
            )
        binding.inputUsername.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_NEXT ||
                (keyEvent?.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                val username = textView.text.toString()
                sharedPreferences.edit().putString("username", username).apply()

                return@setOnEditorActionListener true
            }
            false
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