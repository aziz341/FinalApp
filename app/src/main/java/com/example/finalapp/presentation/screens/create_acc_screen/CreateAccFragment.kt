package com.example.finalapp.presentation.screens.create_acc_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentCreateAccBinding

class CreateAccFragment : Fragment() {

    private val binding:FragmentCreateAccBinding by lazy {
        FragmentCreateAccBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.setOnClickListener {
            findNavController().navigate(R.id.action_createAccFragment_to_registerFragment)
        }
        binding.txtLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_createAccFragment_to_loginFragment)
        }
    }
}