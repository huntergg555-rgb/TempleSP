package com.example.templesp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class FacebookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)

        val btnContinueFb = findViewById<MaterialButton>(R.id.btnContinueFb)
        val btnCancelFb = findViewById<MaterialButton>(R.id.btnCancelFb)

        // กด ดำเนินการต่อ -> ถือว่าล็อกอินสำเร็จ ไปหน้า MainActivity
        btnContinueFb.setOnClickListener {
            Toast.makeText(this, "เข้าสู่ระบบด้วย Facebook สำเร็จ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            // เคลียร์ประวัติหน้าจอเก่าๆ ทิ้งให้หมด เพื่อไม่ให้กดย้อนกลับมาหน้า Facebook หรือ Login ได้อีก
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // กด ยกเลิก -> ปิดหน้านี้ทิ้ง กลับไปหน้า Login
        btnCancelFb.setOnClickListener {
            finish()
        }
    }
}