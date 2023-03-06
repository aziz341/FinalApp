package com.example.finalapp.presentation.screens.profile_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalapp.databinding.FragmentProfileBinding
import com.parse.ParseUser

class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = ParseUser.getCurrentUser().username
        val userEmail = ParseUser.getCurrentUser().email
        binding.txtName.text = userName
    }
}