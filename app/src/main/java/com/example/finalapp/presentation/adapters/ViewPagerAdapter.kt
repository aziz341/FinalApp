package com.example.finalapp.presentation.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalapp.presentation.screens.home_screen.HomeFragment
import com.example.finalapp.presentation.screens.popular_recipes_screen.PopularRecipesFragment

class ViewPagerAdapter(list: List<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    private val fragmentList = list
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                HomeFragment()
            }
            1 -> {
                PopularRecipesFragment()
            }
            else -> {throw Resources.NotFoundException("Position Not Found")
            }
    }
}
}