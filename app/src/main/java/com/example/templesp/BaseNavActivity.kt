package com.example.templesp

import android.content.Intent
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

abstract class BaseNavActivity : AppCompatActivity() {

    abstract fun getCurrentIconRes(): Int
    abstract fun getCurrentNavId(): Int

    private var isMenuExpanded = false

    protected fun setupPillNav() {
        val mainIcon = findViewById<ImageView>(R.id.navMainIcon) ?: return
        val hiddenIcons = findViewById<LinearLayout>(R.id.hiddenNavIcons) ?: return

        // ตั้งไอคอนหน้าปัจจุบัน
        mainIcon.setImageResource(getCurrentIconRes())

        // ซ่อนไอคอนของหน้าปัจจุบัน
        hideCurrentPageIcon()

        // Tint ไอคอนทั้งหมดให้เป็นสีเทาเข้ม
        tintAllIcons()

        // กดไอคอนหลัก → ขยาย/ยุบเมนูด้วย spring animation
        mainIcon.setOnClickListener {
            toggleMenu(mainIcon, hiddenIcons)
        }

        // กดแต่ละไอคอนเพื่อเปลี่ยนหน้า (พร้อม scale feedback)
        setupNavClick(R.id.navIconHome, MainActivity::class.java)
        setupNavClick(R.id.navIconMap, MapActivity::class.java)
        setupNavClick(R.id.navIconTemple, TempleInfoActivity::class.java)
        setupNavClick(R.id.navIconSocial, SocialActivity::class.java)
        setupNavClick(R.id.navIconCheckpoint, CheckpointActivity::class.java)
        setupNavClick(R.id.navIconProfile, ProfileActivity::class.java)

        // Entrance animation สำหรับ pill
        val pillContent = findViewById<LinearLayout>(R.id.navPillContent)
        pillContent?.let {
            it.alpha = 0f
            it.translationY = 60f
            it.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(200)
                .setInterpolator(OvershootInterpolator(1.2f))
                .start()
        }
    }

    private fun hideCurrentPageIcon() {
        findViewById<ImageView>(getCurrentNavId())?.visibility = View.GONE
    }

    private fun tintAllIcons() {
        val darkColor = ContextCompat.getColor(this, android.R.color.darker_gray)
        listOf(R.id.navIconHome, R.id.navIconMap, R.id.navIconTemple, R.id.navIconSocial, R.id.navIconCheckpoint, R.id.navIconProfile).forEach {
            findViewById<ImageView>(it)?.setColorFilter(darkColor)
        }
    }

    private fun toggleMenu(mainIcon: ImageView, hiddenIcons: LinearLayout) {
        if (isMenuExpanded) {
            collapseMenu(mainIcon, hiddenIcons)
        } else {
            expandMenu(mainIcon, hiddenIcons)
        }
        isMenuExpanded = !isMenuExpanded
    }

    private fun expandMenu(mainIcon: ImageView, hiddenIcons: LinearLayout) {
        hiddenIcons.visibility = View.VISIBLE
        hiddenIcons.alpha = 0f
        hiddenIcons.scaleX = 0.5f
        hiddenIcons.scaleY = 0.5f

        // Fade + Scale ชุดไอคอน
        hiddenIcons.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(280)
            .setInterpolator(OvershootInterpolator(1.5f))
            .start()

        // หมุนไอคอนหลัก
        mainIcon.animate()
            .rotation(45f)
            .scaleX(0.85f)
            .scaleY(0.85f)
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(2f))
            .start()

        // Stagger animation แต่ละไอคอน
        animateIconsStagger(hiddenIcons, true)
    }

    private fun collapseMenu(mainIcon: ImageView, hiddenIcons: LinearLayout) {
        hiddenIcons.animate()
            .alpha(0f)
            .scaleX(0.5f)
            .scaleY(0.5f)
            .setDuration(200)
            .withEndAction { hiddenIcons.visibility = View.GONE }
            .start()

        mainIcon.animate()
            .rotation(0f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(2f))
            .start()
    }

    /** แอนิเมชันไอคอนทีละตัว (stagger) */
    private fun animateIconsStagger(container: LinearLayout, show: Boolean) {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child.visibility == View.GONE) continue

            child.alpha = if (show) 0f else 1f
            child.translationX = if (show) -30f else 0f

            child.animate()
                .alpha(if (show) 1f else 0f)
                .translationX(0f)
                .setDuration(200)
                .setStartDelay((i * 40).toLong())
                .setInterpolator(OvershootInterpolator(1f))
                .start()
        }
    }

    private fun setupNavClick(iconViewId: Int, targetActivity: Class<*>) {
        val icon = findViewById<ImageView>(iconViewId) ?: return

        icon.setOnClickListener { view ->
            // กด feedback: scale down แล้ว scale up (bounce)
            view.animate()
                .scaleX(0.7f)
                .scaleY(0.7f)
                .setDuration(80)
                .withEndAction {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(120)
                        .setInterpolator(OvershootInterpolator(3f))
                        .withEndAction {
                            // เปลี่ยนหน้าพร้อม transition
                            val intent = Intent(this, targetActivity)
                            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                            startActivity(intent)
                            @Suppress("DEPRECATION")
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                        .start()
                }
                .start()
        }
    }

    override fun onPause() {
        super.onPause()
        // ยุบเมนูเวลาออกจากหน้า เพื่อไม่ให้ค้างตอนกลับมา
        if (isMenuExpanded) {
            val hiddenIcons = findViewById<LinearLayout>(R.id.hiddenNavIcons)
            hiddenIcons?.visibility = View.GONE
            val mainIcon = findViewById<ImageView>(R.id.navMainIcon)
            mainIcon?.rotation = 0f
            mainIcon?.scaleX = 1f
            mainIcon?.scaleY = 1f
            isMenuExpanded = false
        }
    }
}
