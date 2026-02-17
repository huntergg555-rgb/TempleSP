package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. ผูกช่องกรอกข้อมูลและปุ่มต่างๆ
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val btnRegister = findViewById<MaterialButton>(R.id.btnRegister)

        // ผูกปุ่ม Facebook จาก id ที่เราตั้งไว้
        val btnFacebook = findViewById<CardView>(R.id.btnFacebook)
        val btnLine = findViewById<CardView>(R.id.btnLine)
        // 2. ตั้งค่าปุ่มเข้าสู่ระบบ (ระบบจำลอง)
        btnLogin.setOnClickListener {
            val usernameInput = etUsername.text.toString().trim()
            val passwordInput = etPassword.text.toString()

            if (usernameInput == "admin" && passwordInput == "1234") {
                Toast.makeText(this, "เข้าสู่ระบบสำเร็จ!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "ชื่อผู้ใช้งานหรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
                etPassword.setText("")
                etPassword.requestFocus()
            }
        }

        // 3. ตั้งค่าปุ่มลงทะเบียน
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 4. ตั้งค่าปุ่ม Facebook !! (โค้ดส่วนนี้คือตัวสั่งให้เปลี่ยนหน้า)
        btnFacebook.setOnClickListener {
            val intent = Intent(this, FacebookActivity::class.java)
            startActivity(intent)
        }
        btnLine.setOnClickListener {
            val intent = Intent(this, LineActivity::class.java)
            startActivity(intent)
        }
    }
}