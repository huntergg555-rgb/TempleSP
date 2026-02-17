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

        // ผูกตัวแปรการ์ดข้อมูลต่างๆ
        val cardNews1 = findViewById<CardView>(R.id.cardNews1)
        val cardNews2 = findViewById<CardView>(R.id.cardNews2)
        val cardFestival = findViewById<CardView>(R.id.cardFestival)

        cardNews1.setOnClickListener { Toast.makeText(this, "เปิดงานประจำปี 1", Toast.LENGTH_SHORT).show() }
        cardNews2.setOnClickListener { Toast.makeText(this, "เปิดงานประจำปี 2", Toast.LENGTH_SHORT).show() }
        cardFestival.setOnClickListener { Toast.makeText(this, "เปิดงานวัดสะพานสูง", Toast.LENGTH_SHORT).show() }

        // --- ส่วนของการจัดการเมนูด้านล่าง ---
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)

        // เมื่อกดที่ไอคอนรูปบ้าน (หน้าปัจจุบัน)
        btnNavHome.setOnClickListener {
            // เช็คว่าถ้ามันซ่อนอยู่ ให้เปิดโชว์ขึ้นมา แต่ถ้าเปิดอยู่แล้ว ให้ซ่อนกลับไป
            if (hiddenMenu.visibility == View.GONE) {
                hiddenMenu.visibility = View.VISIBLE
            } else {
                hiddenMenu.visibility = View.GONE
            }
        }

        // เมื่อกดที่ไอคอนรูปโปรไฟล์ (ที่เพิ่งเด้งขึ้นมา)
        btnNavProfile.setOnClickListener {
            // สั่งให้ซ่อนเมนูกลับไปก่อน (เพื่อความสวยงาม)
            hiddenMenu.visibility = View.GONE

            // เปลี่ยนไปหน้า Profile
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}