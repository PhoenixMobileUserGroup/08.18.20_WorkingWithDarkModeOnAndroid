package com.neudesic.todo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = preferences.getString("theme", "Auto")

        when (theme) {
            "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "Auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            "Battery Saver" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }
    }
}