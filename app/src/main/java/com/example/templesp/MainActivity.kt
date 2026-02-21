package com.example.templesp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ผูกตัวแปรการ์ดข้อมูลต่างๆ
        val cardNews1 = findViewById<CardView>(R.id.cardNews1)
        val cardFestival = findViewById<CardView>(R.id.cardFestival)

        // ==========================================
        // ส่งข้อมูล Pop-up ของ "งานประจำปี" (ยอดบริจาค)
        // ==========================================
        cardNews1.setOnClickListener {
            showInfoDialog(
                "สรุปยอดบริจาค งานประจำปี",
                "📅 27 ธ.ค. 2568 - 5 ม.ค. 2569",
                "1. เจ้าภาพร่วมทำบุญ:\n    ๓๒๒,๗๗๐ บาท\n\n2. ร้านค้าและอื่นๆ:\n    ๒,๔๑๑,๔๒๓ บาท"
            )
        }

        // ==========================================
        // ส่งข้อมูล Pop-up ของ "งานวัดสะพานสูง" (คอนเสิร์ต)
        // ==========================================
        cardFestival.setOnClickListener {
            showInfoDialog(
                "งานนมัสการพระบรมสารีริกธาตุ ปิดทอง",
                "📅 27 ธ.ค. 68 - 5 ม.ค. 69 (10 วัน 10 คืน)",
                "ทำบุญอุทิศถวายแด่บูรพาจารย์\nหลวงปู่เอี่ยม หลวงปู่กลิ่น หลวงปู่ทองสุข\n\n✨ ชมฟรี!! มหรสพตลอดงาน ✨\nพบกับศิลปินดังมากมาย เช่น:\n- ตั๊กแตน ชลดา\n- เปาวลี\n- แอน อรดี\n- สาวน้อยเพชรบ้านแพง\n- และอีกเพียบ!"
            )
        }

        // --- ส่วนของการจัดการเมนูด้านล่าง ---
        val hiddenMenu = findViewById<LinearLayout>(R.id.hiddenMenu)
        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)
        val btnNavProfile = findViewById<ImageView>(R.id.btnNavProfile)

        btnNavHome.setOnClickListener {
            if (hiddenMenu.visibility == View.GONE) {
                hiddenMenu.visibility = View.VISIBLE
            } else {
                hiddenMenu.visibility = View.GONE
            }
        }

        btnNavProfile.setOnClickListener {
            hiddenMenu.visibility = View.GONE
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    // ==========================================
    // ฟังก์ชันสร้าง Pop-up (รับค่า 3 อย่าง: หัวข้อ, วันที่, รายละเอียด)
    // ==========================================
    private fun showInfoDialog(title: String, date: String, details: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_temple_info)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        // ผูกตัวแปรในหน้า Pop-up
        val tvDialogTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogDate = dialog.findViewById<TextView>(R.id.tvDialogDate)
        val tvDialogDetails = dialog.findViewById<TextView>(R.id.tvDialogDetails)
        val btnCloseDialog = dialog.findViewById<MaterialButton>(R.id.btnCloseDialog)

        // เอาข้อความที่เราส่งมา ไปแปะใส่ใน Pop-up
        tvDialogTitle.text = title
        tvDialogDate.text = date
        tvDialogDetails.text = details

        btnCloseDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}