package com.neudesic.todo.feature.settings.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import com.neudesic.todo.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)

        val dropDownPreference = findPreference<DropDownPreference>("theme")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dropDownPreference?.entries = resources.getStringArray(R.array.app_themes)
            dropDownPreference?.entryValues = resources.getStringArray(R.array.app_themes)
        } else {
            dropDownPreference?.entries = resources.getStringArray(R.array.app_themes_legacy)
            dropDownPreference?.entryValues = resources.getStringArray(R.array.app_themes_legacy)
        }
        dropDownPreference?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue.toString()) {
                "Light" -> setDefaultNightMode(MODE_NIGHT_NO)
                "Dark" -> setDefaultNightMode(MODE_NIGHT_YES)
                "Auto" -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                "Battery Saver" -> setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY)
            }
            true
        }
    }
}