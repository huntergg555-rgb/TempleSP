package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class ProfileActivity : BaseNavActivity() {

    override fun getCurrentIconRes(): Int = R.drawable.ic_person
    override fun getCurrentNavId(): Int = R.id.navIconProfile

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupPillNav()

        val icEye = findViewById<ImageView>(R.id.icEye)
        val tvPassword = findViewById<TextView>(R.id.tvPassword)

        icEye.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                tvPassword.text = "123456789"
                icEye.setImageResource(android.R.drawable.ic_menu_view)
                Toast.makeText(this, "แสดงรหัสผ่าน", Toast.LENGTH_SHORT).show()
            } else {
                tvPassword.text = "1********9"
                icEye.setImageResource(android.R.drawable.ic_menu_view)
            }
        }

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener { finish() }

        findViewById<CardView>(R.id.btnEditProfilePic).setOnClickListener { Toast.makeText(this, "แก้ไขรูปโปรไฟล์", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditEmail).setOnClickListener { Toast.makeText(this, "แก้ไขอีเมล", Toast.LENGTH_SHORT).show() }
        findViewById<ImageView>(R.id.btnEditPassword).setOnClickListener { Toast.makeText(this, "แก้ไขรหัสผ่าน", Toast.LENGTH_SHORT).show() }

        findViewById<CardView>(R.id.btnFacebookProfile).setOnClickListener { Toast.makeText(this, "ตั้งค่า Facebook", Toast.LENGTH_SHORT).show() }
        findViewById<CardView>(R.id.btnLineProfile).setOnClickListener { Toast.makeText(this, "ตั้งค่า LINE", Toast.LENGTH_SHORT).show() }

        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(this, "ออกจากระบบเรียบร้อย", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}