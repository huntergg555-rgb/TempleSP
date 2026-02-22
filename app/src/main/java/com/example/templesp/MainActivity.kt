package com.example.templesp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton

class MainActivity : BaseNavActivity() {

    override fun getCurrentIconRes(): Int = R.drawable.ic_home
    override fun getCurrentNavId(): Int = R.id.navIconHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPillNav()

        val cardNews1 = findViewById<CardView>(R.id.cardNews1)
        val cardFestival = findViewById<CardView>(R.id.cardFestival)

        cardNews1.setOnClickListener {
            showInfoDialog(
                "สรุปยอดบริจาค งานประจำปี",
                "📅 27 ธ.ค. 2568 - 5 ม.ค. 2569",
                "1. เจ้าภาพร่วมทำบุญ:\n    ๓๒๒,๗๗๐ บาท\n\n2. ร้านค้าและอื่นๆ:\n    ๒,๔๑๑,๔๒๓ บาท"
            )
        }

        cardFestival.setOnClickListener {
            showInfoDialog(
                "งานนมัสการพระบรมสารีริกธาตุ ปิดทอง",
                "📅 27 ธ.ค. 68 - 5 ม.ค. 69 (10 วัน 10 คืน)",
                "ทำบุญอุทิศถวายแด่บูรพาจารย์\nหลวงปู่เอี่ยม หลวงปู่กลิ่น หลวงปู่ทองสุข\n\n✨ ชมฟรี!! มหรสพตลอดงาน ✨\nพบกับศิลปินดังมากมาย เช่น:\n- ตั๊กแตน ชลดา\n- เปาวลี\n- แอน อรดี\n- สาวน้อยเพชรบ้านแพง\n- และอีกเพียบ!"
            )
        }
    }

    private fun showInfoDialog(title: String, date: String, details: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_temple_info)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val tvDialogTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogDate = dialog.findViewById<TextView>(R.id.tvDialogDate)
        val tvDialogDetails = dialog.findViewById<TextView>(R.id.tvDialogDetails)
        val btnCloseDialog = dialog.findViewById<MaterialButton>(R.id.btnCloseDialog)

        tvDialogTitle.text = title
        tvDialogDate.text = date
        tvDialogDetails.text = details

        btnCloseDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}