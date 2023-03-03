package com.example.finalapp.presentation.screens.login_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentLoginBinding
import com.parse.ParseUser


class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            ParseUser.logIn(
                binding.userName.text.toString(),
                binding.passwordEt.text.toString()
            )
            navigateToMainScreen()
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }
        binding.imageView2.setOnClickListener {
            login()
        }
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(
            R.id.general_nav,
            bundleOf(),
            createNavOptionsWithAnimations()
        )
    }

    private fun createNavOptionsWithAnimations() = NavOptions
        .Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    private fun login() {
        viewModel.login(
            binding.userName.text.toString(),
            binding.passwordEt.text.toString()
        )
    }


}