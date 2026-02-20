package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ผูกตัวแปรการ์ดข้อมูลต่างๆ (เหลือแค่ที่มีอยู่ใน XML)
        findViewById<CardView>(R.id.cardNews1).setOnClickListener { 
            Toast.makeText(this, "เปิดงานประจำปี", Toast.LENGTH_SHORT).show() 
        }
        
        findViewById<CardView>(R.id.cardFestival).setOnClickListener { 
            Toast.makeText(this, "เปิดงานวัดสะพานสูง", Toast.LENGTH_SHORT).show() 
        }

        // --- ส่วนของการจัดการเมนูด้านล่าง ---
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)
        val btnNavSocial = findViewById<ImageView>(R.id.btnNavSocial)
        val btnNavMap = findViewById<ImageView>(R.id.btnNavMap)

        // ปุ่มหลัก (Home) ทำหน้าที่เปิด/ปิดเมนู
        btnNavHome.setOnClickListener {
            if (hiddenMenu.visibility == View.GONE) {
                hiddenMenu.visibility = View.VISIBLE
            } else {
                hiddenMenu.visibility = View.GONE
            }
        }

        // เชื่อมต่อแต่ละปุ่มในเมนูที่ซ่อนอยู่
        btnNavProfile.setOnClickListener {
            hiddenMenu.visibility = View.GONE
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnNavSocial.setOnClickListener {
            hiddenMenu.visibility = View.GONE
            startActivity(Intent(this, SocialActivity::class.java))
        }

        btnNavMap.setOnClickListener {
            hiddenMenu.visibility = View.GONE
            startActivity(Intent(this, MapActivity::class.java))
        }
    }
}