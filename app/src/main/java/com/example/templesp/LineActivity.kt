package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line)

        // ผูกตัวแปรปุ่มต่างๆ จาก XML
        val tvCancelTop = findViewById<TextView>(R.id.tvCancelTop)
        val tvCancelBottom = findViewById<TextView>(R.id.tvCancelBottom)
        val btnAllowLine = findViewById<MaterialButton>(R.id.btnAllowLine)

        // 1. กดปุ่ม "อนุญาต" (สีเขียว) -> ล็อกอินสำเร็จ ไปหน้า MainActivity
        btnAllowLine.setOnClickListener {
            Toast.makeText(this, "เข้าสู่ระบบด้วย LINE สำเร็จ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            // เคลียร์ประวัติหน้าเก่า เพื่อไม่ให้กดย้อนกลับมาหน้า LINE หรือ Login ได้อีก
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // 2. กด "ยกเลิก" (มุมขวาบน) -> ปิดหน้า LINE ทิ้ง กลับไปหน้า Login
        tvCancelTop.setOnClickListener {
            finish()
        }

        // 3. กด "ยกเลิก" (ตรงกลางล่างสุด) -> ปิดหน้า LINE ทิ้ง กลับไปหน้า Login
        tvCancelBottom.setOnClickListener {
            finish()
        }
    }
}