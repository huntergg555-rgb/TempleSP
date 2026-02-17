package com.example.templesp

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. ผูกปุ่มและช่องกรอกข้อมูลทั้งหมดจากไฟล์ XML
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etRegPassword = findViewById<EditText>(R.id.etRegPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnSubmitRegister = findViewById<MaterialButton>(R.id.btnSubmitRegister)

        // 2. สั่งให้ทำงานเมื่อปุ่มถูกกด
        btnSubmitRegister.setOnClickListener {

            // ดึงข้อความจากช่องต่างๆ มาเก็บไว้
            val emailInput = etEmail.text.toString().trim()
            val passwordInput = etRegPassword.text.toString()
            val confirmPasswordInput = etConfirmPassword.text.toString()

            // 3. เริ่มตรวจสอบเงื่อนไขทีละข้อ
            if (!emailInput.endsWith("@gmail.com")) {
                // เช็คข้อ 1: อีเมลต้องลงท้ายด้วย @gmail.com
                etEmail.error = "กรุณาใช้อีเมล @gmail.com เท่านั้น"
                etEmail.requestFocus()

            } else if (passwordInput.isEmpty()) {
                // เช็คข้อ 2: ห้ามปล่อยช่องรหัสผ่านว่างเปล่า
                etRegPassword.error = "กรุณากรอกรหัสผ่าน"
                etRegPassword.requestFocus()

            } else if (passwordInput != confirmPasswordInput) {
                // เช็คข้อ 3: รหัสผ่าน และ ยืนยันรหัสผ่าน ต้องเหมือนกันเป๊ะ (!= แปลว่า ไม่เท่ากับ)
                etConfirmPassword.error = "รหัสผ่านไม่ตรงกัน"
                etConfirmPassword.requestFocus()

            } else {
                // --- ถ้าผ่านทุกเงื่อนไข (ข้อมูลถูกต้องทั้งหมด) ---
                Toast.makeText(this, "ลงทะเบียนสำเร็จ!", Toast.LENGTH_SHORT).show()
                finish() // ปิดหน้านี้เพื่อกลับไปหน้า Login
            }
        }
    }
}