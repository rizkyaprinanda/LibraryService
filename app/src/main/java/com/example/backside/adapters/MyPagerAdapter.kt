package com.example.backside.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.backside.FirstFragment
import com.example.backside.SecondFragment
import androidx.lifecycle.Lifecycle
import com.example.backside.ThirdFragment

class MyPagerAdapter(fragmentManager:  FragmentManager, lifecycle : Lifecycle ) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        // Sesuaikan dengan jumlah dan jenis fragmen atau aktivitas yang Anda miliki
        when (position) {
            0 ->  FirstFragment()
            1 ->  SecondFragment()
            2 ->  ThirdFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
        return  fragment
    }
}
