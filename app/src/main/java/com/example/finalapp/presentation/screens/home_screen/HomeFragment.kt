package com.example.finalapp.presentation.screens.home_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentHomeBinding
import com.example.finalapp.presentation.adapters.PostAdapter
import com.example.finalapp.presentation.viewModels.HomeViewModel
import com.example.finalapp.domain.model.Post
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment(),PostAdapter.Listener {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val fragmentList = mutableListOf<Fragment>()

    private val adapter: PostAdapter by lazy {
        PostAdapter(this)
    }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllPosts()
        viewModel.posts.observe(viewLifecycleOwner) {
            if (it.isSuccessful){
                adapter.postList = it.body()?.posts as List<Post>
            }
        }
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter
        }
    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view) ?: null
        bottomNav?.isVisible = true
    }
    private fun navigateToDetailsScreen() {
        findNavController().navigate(
            R.id.action_navigation_home_to_menuFragment,
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

    override fun onClick(post: Post) {
    navigateToDetailsScreen()
    }
}
