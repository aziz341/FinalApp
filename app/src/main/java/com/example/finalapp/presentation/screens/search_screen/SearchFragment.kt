package com.example.finalapp.presentation.screens.search_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private val binding:FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}