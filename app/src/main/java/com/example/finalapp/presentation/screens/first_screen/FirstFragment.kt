package com.example.finalapp.presentation.screens.first_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentSplashBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FirstFragment : Fragment() {

    private val binding: FragmentSplashBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartCooking.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_createAccFragment)
        }
    }
    //    private fun introScreen() {
//        findNavController().navigate(
//            FirstFragmentDirections.actionFirstFragmentToCreateAccFragment())
//    }

    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view) ?: null
        bottomNav?.isVisible = false
    }

}