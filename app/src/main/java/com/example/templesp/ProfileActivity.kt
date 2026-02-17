package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    // ตัวแปรสำหรับสลับการซ่อน/แสดงรหัสผ่าน
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // --- ส่วนของการจัดการรหัสผ่าน (เปิด/ปิดตา) ---
        val icEye = findViewById<ImageView>(R.id.icEye)
        val tvPassword = findViewById<TextView>(R.id.tvPassword)

        icEye.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                tvPassword.text = "123456789"
                Toast.makeText(this, "แสดงรหัสผ่าน", Toast.LENGTH_SHORT).show()
            } else {
                tvPassword.text = "1********9"
            }
        }

        // --- ส่วนการกดปุ่มแก้ไขต่างๆ และ Logout ---
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener { finish() }

        findViewById<CardView>(R.id.btnEditProfilePic).setOnClickListener { Toast.makeText(this, "แก้ไขรูปโปรไฟล์", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditName).setOnClickListener { Toast.makeText(this, "แก้ไขชื่อ", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditEmail).setOnClickListener { Toast.makeText(this, "แก้ไขอีเมล", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditPassword).setOnClickListener { Toast.makeText(this, "แก้ไขรหัสผ่าน", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditPhone).setOnClickListener { Toast.makeText(this, "แก้ไขเบอร์โทรศัพท์", Toast.LENGTH_SHORT).show() }
        findViewById<CardView>(R.id.btnFacebookProfile).setOnClickListener { Toast.makeText(this, "ตั้งค่า Facebook", Toast.LENGTH_SHORT).show() }
        findViewById<CardView>(R.id.btnLineProfile).setOnClickListener { Toast.makeText(this, "ตั้งค่า LINE", Toast.LENGTH_SHORT).show() }

        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(this, "ออกจากระบบเรียบร้อย", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // --- ส่วนของการจัดการเมนูด้านล่าง (ยืด/หดได้) ---
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)

        // เมื่อกดที่ไอคอนรูปโปรไฟล์ (หน้าปัจจุบัน) ให้เปิด/ปิด เมนูที่ซ่อนอยู่
        btnNavProfile.setOnClickListener {
            if (hiddenMenu.visibility == View.GONE) {
                hiddenMenu.visibility = View.VISIBLE
            } else {
                hiddenMenu.visibility = View.GONE
            }
        }

        // เมื่อกดที่ไอคอนรูปบ้าน (ที่ยืดขึ้นมา) ให้กลับไปหน้าหลัก (MainActivity)
        btnNavHome.setOnClickListener {
            hiddenMenu.visibility = View.GONE
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // ปิดหน้าโปรไฟล์ทิ้ง เพื่อไม่ให้ซ้อนกันหลายหน้า
        }
    }
}