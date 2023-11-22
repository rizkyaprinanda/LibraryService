package com.example.backside

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainMenuActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemHome -> {
                    val fragment = HomeFragment.newInstance()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.itemBrowser -> {

                    val fragment = BrowserFragment.newInstance()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.itemBuat -> {

                    val fragment = PurchasedFragment.newInstance()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.itemProfile -> {

                    val fragment = ProfileFragment.newInstance()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }


            }
            false
        }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Tampilkan Fragment Home saat pertama kali activity dibuka
        val fragment = HomeFragment.newInstance()
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                com.google.android.material.R.anim.design_bottom_sheet_slide_in,
                com.google.android.material.R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.fragment, fragment, fragment.javaClass.simpleName) // Gunakan simpleName
            .commit() // Tambahkan ini untuk mengeksekusi perubahan
    }

}