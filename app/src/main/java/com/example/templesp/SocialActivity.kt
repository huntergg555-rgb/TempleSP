package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SocialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)

        // ระบบเมนูเด้ง
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavSocial = findViewById<ImageView>(R.id.btnNavSocial)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)
        val btnNavMap = findViewById<ImageView>(R.id.btnNavMap)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)

        btnNavSocial.setOnClickListener {
            hiddenMenu.visibility = if (hiddenMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnNavHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        btnNavMap.setOnClickListener { startActivity(Intent(this, MapActivity::class.java)) }
        btnNavProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }
}