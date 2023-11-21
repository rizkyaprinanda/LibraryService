package com.example.backside

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.backside.adapters.MyPagerAdapter
import com.example.backside.databinding.ActivityMulaiBinding
import com.google.android.material.tabs.TabLayoutMediator

class Mulai : AppCompatActivity() {
    private lateinit var binding : ActivityMulaiBinding
    private lateinit var viewPagerAdapter: MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMulaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerAdapter = MyPagerAdapter(supportFragmentManager, lifecycle)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)


        with(binding){
            viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                when(position) {
                    0 -> tab.text = "Halaman 1"
                    1 -> tab.text = "Halaman 2"
                    2 -> tab.text = "Halaman 3"
                }
            }.attach()
        }


    }
}
