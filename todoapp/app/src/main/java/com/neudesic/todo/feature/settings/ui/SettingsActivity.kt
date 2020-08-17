package com.neudesic.todo.feature.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neudesic.todo.R
import com.neudesic.todo.feature.settings.ui.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SettingsFragment())
            .commit()
    }
}