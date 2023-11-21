package com.example.backside

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class TutorialTheme : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}