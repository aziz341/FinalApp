package com.example.finalapp.presentation.screens.menu_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalapp.databinding.FragmentMenuBinding
import com.example.finalapp.domain.model.Post
import com.squareup.picasso.Picasso

class MenuFragment : Fragment() {

    private val binding: FragmentMenuBinding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    private lateinit var posts: Post


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }


    private fun setupUi() {
        binding.apply {
            txtName.text = posts.postTitle
            txtUserName.text = posts.user_name
            txtKkal.text = posts.kkal
            txtPrepTime.text = posts.post_preptime
            txtCookTime.text = posts.post_cooktime
            txtDescription.text = posts.post_description
            Picasso.get().load(posts.post_image.url).into(img)
        }
    }

}