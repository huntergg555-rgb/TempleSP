package com.example.templesp


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SocialActivity : AppCompatActivity() {

    // 1. ประกาศตัวแปรสำหรับเชื่อมกับหน้า UI
    private lateinit var btnGallery: ImageButton
    private lateinit var btnRemoveImage: ImageButton
    private lateinit var btnPost: Button
    private lateinit var etPostText: EditText
    private lateinit var imagePreviewContainer: FrameLayout
    private lateinit var ivPreviewImage: ImageView

    // 2. ตัวแปรสำหรับเก็บที่อยู่ (Uri) ของรูปภาพที่ผู้ใช้เลือก
    private var selectedImageUri: Uri? = null

    // 3. สร้างระบบเปิดแกลลอรี่ (วิธีใหม่ล่าสุดของ Android ปลอดภัยและง่าย)
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            // ถ้าผู้ใช้เลือกรูปมา ให้เก็บค่า Uri ไว้
            selectedImageUri = uri

            // เอารูปไปโชว์ในกล่อง Preview
            ivPreviewImage.setImageURI(uri)

            // เปิดให้กล่อง Preview แสดงขึ้นมา (จากเดิมที่ซ่อนไว้)
            imagePreviewContainer.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)

        // 4. ผูกตัวแปรเข้ากับ ID ในหน้า activity_social.xml
        btnGallery = findViewById(R.id.btnGallery)
        btnRemoveImage = findViewById(R.id.btnRemoveImage)
        btnPost = findViewById(R.id.btnPost)
        etPostText = findViewById(R.id.etPostText)
        imagePreviewContainer = findViewById(R.id.imagePreviewContainer)
        ivPreviewImage = findViewById(R.id.ivPreviewImage)

        // --- เริ่มเขียนคำสั่งเมื่อกดปุ่มต่างๆ ---

        // ปุ่มที่ 1: กดแกลลอรี่ -> สั่งให้เปิดหน้าเลือกรูปภาพ
        btnGallery.setOnClickListener {
            // "image/*" หมายถึงให้เลือกได้เฉพาะไฟล์รูปภาพเท่านั้น
            pickImageLauncher.launch("image/*")
        }

        // ปุ่มที่ 2: กดกากบาทมุมขวาบนรูป -> ลบทิ้งและซ่อนกล่องรูป
        btnRemoveImage.setOnClickListener {
            selectedImageUri = null
            ivPreviewImage.setImageURI(null)
            imagePreviewContainer.visibility = View.GONE
        }

        // ปุ่มที่ 3: กดโพสต์
        btnPost.setOnClickListener {
            val postText = etPostText.text.toString().trim()

            // เช็คก่อนว่าพิมพ์ข้อความหรือใส่รูปหรือยัง ถ้าว่างเปล่าทั้งคู่จะไม่ให้โพสต์
            if (postText.isEmpty() && selectedImageUri == null) {
                Toast.makeText(this, "กรุณาพิมพ์ข้อความหรือเลือกรูปภาพ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🟢 ตรงนี้คือจุดที่พี่ต้องเอาข้อมูลไปส่งขึ้นเซิร์ฟเวอร์ หรือบันทึกลง Database 🟢
            // ตัวอย่างแค่แสดง Toast ให้รู้ว่าระบบทำงาน
            Toast.makeText(this, "โพสต์สำเร็จ!", Toast.LENGTH_SHORT).show()

            // พอโพสต์เสร็จ ก็เคลียร์กล่องข้อความและซ่อนรูปภาพให้กลับไปเป็นเหมือนเดิม
            etPostText.text.clear()
            selectedImageUri = null
            imagePreviewContainer.visibility = View.GONE
        }
    }
}