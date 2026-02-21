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

        // ==========================================
        // 1. ส่วนจัดการรหัสผ่าน (เปิด/ปิดตา)
        // ==========================================
        val icEye = findViewById<ImageView>(R.id.icEye)
        val tvPassword = findViewById<TextView>(R.id.tvPassword)

        icEye.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // แสดงรหัสผ่านจริง
                tvPassword.text = "123456789"
                icEye.setImageResource(android.R.drawable.ic_menu_view) // เปลี่ยนไอคอนเป็นรูปตาเปิด
                Toast.makeText(this, "แสดงรหัสผ่าน", Toast.LENGTH_SHORT).show()
            } else {
                // ซ่อนรหัสผ่าน
                tvPassword.text = "1********9"
                icEye.setImageResource(android.R.drawable.ic_menu_view) // หรือไอคอนตาปิดถ้าคุณมี
            }
        }

        // ==========================================
        // 2. ส่วนการกดปุ่มแก้ไขข้อมูลต่างๆ
        // ==========================================
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener { finish() }

        findViewById<CardView>(R.id.btnEditProfilePic).setOnClickListener { Toast.makeText(this, "แก้ไขรูปโปรไฟล์", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditEmail).setOnClickListener { Toast.makeText(this, "แก้ไขอีเมล", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditPassword).setOnClickListener { Toast.makeText(this, "แก้ไขรหัสผ่าน", Toast.LENGTH_SHORT).show() }

        // ปุ่มโซเชียล
        findViewById<CardView>(R.id.btnFacebookProfile).setOnClickListener { Toast.makeText(this, "ตั้งค่า Facebook", Toast.LENGTH_SHORT).show() }
        findViewById<CardView>(R.id.btnLineProfile).setOnClickListener { Toast.makeText(this, "ตั้งค่า LINE", Toast.LENGTH_SHORT).show() }

        // ==========================================
        // 3. ปุ่มออกจากระบบ (Logout)
        // ==========================================
        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(this, "ออกจากระบบเรียบร้อย", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            // เคลียร์ประวัติการเปิดหน้าแอปทั้งหมด เพื่อความปลอดภัย
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // ==========================================
        // 4. การจัดการเมนูด้านล่าง (Bottom Navigation)
        // ==========================================
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)

        // เมื่อกดที่ไอคอนโปรไฟล์ในแถบเขียว (หน้าปัจจุบัน) -> ให้เมนู Home เด้งขึ้นมา
        btnNavProfile.setOnClickListener {
            if (hiddenMenu.visibility == View.GONE) {
                hiddenMenu.visibility = View.VISIBLE
            } else {
                hiddenMenu.visibility = View.GONE
            }
        }

        // เมื่อกดที่ไอคอนรูปบ้านที่เด้งขึ้นมา -> กลับไปหน้าหลัก
        btnNavHome.setOnClickListener {
            hiddenMenu.visibility = View.GONE
            val intent = Intent(this, MainActivity::class.java)
            // สั่งให้ดึงหน้า Main เดิมขึ้นมา ไม่ต้องเปิดหน้าใหม่ซ้ำ
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            finish() // ปิดหน้าโปรไฟล์
        }
    }
}