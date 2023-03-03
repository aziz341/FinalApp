package com.example.finalapp.presentation.screens.register_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentRegisterBinding
import com.example.finalapp.domain.model.User
import com.parse.ParseUser


class RegisterFragment : Fragment() {
    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                ParseUser.logIn(
                    binding.userName.text.toString(),
                    binding.passwordEt.text.toString()
                )
                navigateToMainScreen()
            } else {
//                viewModel.error.ob
            }
        }

        binding.createAccountRegister.setOnClickListener {
            Log.i("joseph", "RegisterFragment -> createAccountRegister")
            signUp()
        }
    }
    private fun navControllerPopBackStackInclusive() =
        findNavController().popBackStack(R.id.reg_log_nav, false)

    private fun navigateToMainScreen() {
        Log.i("joseph", "RegisterFragment -> navigateToMainScreen")
        navControllerPopBackStackInclusive()
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

    private fun signUp() {
        val user = User(
            binding.userName.text.toString(),
            binding.emailEt.text.toString(),
            binding.passwordEt.text.toString()
        )
        viewModel.signUp(user)
    }


}