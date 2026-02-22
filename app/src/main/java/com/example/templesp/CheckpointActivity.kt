package com.example.templesp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class CheckpointActivity : AppCompatActivity() {

    private lateinit var hScroll: HorizontalScrollView
    private lateinit var vScroll: ScrollView
    private lateinit var imageContainer: FrameLayout
    private lateinit var infoCard: CardView
    private lateinit var infoTitle: TextView
    private lateinit var infoDesc: TextView
    private lateinit var infoHighlight: TextView
    private lateinit var infoBadge: TextView
    private lateinit var txtHint: TextView
    private lateinit var btnCheckIn: TextView
    private lateinit var stampProgress: ProgressBar
    private lateinit var txtProgressLabel: TextView
    private lateinit var txtPinCount: TextView
    private lateinit var prefs: SharedPreferences

    private var currentCheckpoint: CheckpointData? = null

    // ข้อมูลของแต่ละจุด checkpoint
    data class CheckpointData(
        val pinId: Int,
        val number: String,
        val title: String,
        val description: String,
        val highlight: String
    )

    private val checkpoints = listOf(
        CheckpointData(
            R.id.pin1, "1",
            "ศาลเจ้าแม่กวนอิม",
            "ศาลเจ้าแม่กวนอิมภายในวัดสะพานสูง เป็นที่สักการบูชาของผู้ศรัทธา เชื่อว่าท่านเป็นพระโพธิสัตว์แห่งความเมตตา\nผู้คนนิยมมากราบไหว้ขอพรเรื่องสุขภาพ ความเมตตา และความสงบสุขในชีวิต",
            "🙏 พระโพธิสัตว์แห่งความเมตตา"
        ),
        CheckpointData(
            R.id.pin2, "2",
            "หลวงปู่เอี่ยมธุดง",
            "หลวงปู่เอี่ยม ปฐมนาม พระเกจิอาจารย์ชื่อดังผู้พัฒนาวัดสะพานสูงให้เจริญรุ่งเรือง ท่านเป็นผู้มีอภิญญาด้านกรรมฐานและวิชาอาคม\nรูปหล่อขนาดเท่าองค์จริง ผู้คนนิยมบนบานเรื่องค้าขาย สุขภาพ หน้าที่การงาน แก้บนด้วยดอกไม้กระทง 7 สี",
            "✨ แหล่งกำเนิดตะกรุดมหาโสฬสมงคล"
        ),
        CheckpointData(
            R.id.pin3, "3",
            "ท่าเรือ & ให้อาหารปลา",
            "ท่าเรือริมคลองพระอุดม เป็นจุดเข้าถึงวัดทางน้ำ สมัยก่อนชาวบ้านนิยมพายเรือมาทำบุญ\nบริเวณนี้ยังเป็นจุดให้อาหารปลา 🐟 ซึ่งมีปลาจำนวนมากอาศัยอยู่ในคลอง เหมาะแก่การทำบุญปล่อยปลาและให้อาหารปลาเพื่อเสริมสิริมงคล",
            "🐟 ทำบุญให้อาหารปลาริมคลอง"
        ),
        CheckpointData(
            R.id.pin4, "4",
            "ตลาดวัดสะพานสูง",
            "บริเวณตลาดนัดภายในวัด มีร้านค้าจำหน่ายอาหาร ขนม ของฝาก และวัตถุมงคล\nในช่วงงานเทศกาลจะคึกคักเป็นพิเศษ มีร้านค้าหลากหลาย ทั้งอาหารพื้นบ้าน ของที่ระลึก และพระเครื่องของหลวงปู่เอี่ยม",
            "🛍️ ช้อปปิ้ง อาหาร วัตถุมงคล"
        ),
        CheckpointData(
            R.id.pin5, "5",
            "พระเจดีย์ประธาน",
            "เจดีย์สีทองโดดเด่นเป็นสง่า เป็นจุดศูนย์กลางของวัด สร้างโดยหลวงปู่เอี่ยมและสานต่อโดยหลวงปู่กลิ่น เจ้าอาวาสรูปที่ 2\nสามารถมองเห็นได้จากระยะไกล ภายในบรรจุพระบรมสารีริกธาตุ",
            "🏛️ เจดีย์สีทองเด่นตระหง่าน"
        ),
        CheckpointData(
            R.id.pin6, "6",
            "สะพานสูง",
            "สะพานข้ามคลองที่เป็นที่มาของชื่อวัด เดิมทีวัดชื่อ \"วัดสว่างอารมณ์\" แต่เมื่อสมเด็จพระมหาสมณเจ้า กรมพระยาวชิรญาณวโรรส เสด็จมาและทอดพระเนตรเห็นสะพานสูงในวัด จึงทรงเปลี่ยนชื่อเป็น \"วัดสะพานสูง\"\nเป็นจุดถ่ายภาพยอดนิยมของนักท่องเที่ยว",
            "📸 ที่มาของชื่อวัดสะพานสูง"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkpoint)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = getSharedPreferences("checkpoint_stamps", MODE_PRIVATE)

        hScroll = findViewById(R.id.hScroll)
        vScroll = findViewById(R.id.vScroll)
        imageContainer = findViewById(R.id.imageContainer)
        infoCard = findViewById(R.id.infoCard)
        infoTitle = findViewById(R.id.infoTitle)
        infoDesc = findViewById(R.id.infoDesc)
        infoHighlight = findViewById(R.id.infoHighlight)
        infoBadge = findViewById(R.id.infoBadge)
        txtHint = findViewById(R.id.txtHint)
        btnCheckIn = findViewById(R.id.btnCheckIn)
        stampProgress = findViewById(R.id.stampProgress)
        txtProgressLabel = findViewById(R.id.txtProgressLabel)
        txtPinCount = findViewById(R.id.txtPinCount)

        // ปุ่มกลับ
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        // ปุ่มปิด info card
        findViewById<ImageView>(R.id.btnCloseCard).setOnClickListener { hideInfoCard() }

        // ปุ่มเช็คอิน
        btnCheckIn.setOnClickListener { checkInCurrentPin() }

        // ตั้ง click listener สำหรับทุก pin
        checkpoints.forEach { cp ->
            val pin = findViewById<ImageView>(cp.pinId)
            setupPinAnimation(pin)
            pin.setOnClickListener {
                bouncePin(pin)
                showCheckpointInfo(cp, pin)
            }
        }

        // โหลดสถานะเช็คอินที่บันทึกไว้
        restoreCheckInState()

        // อัปเดต progress bar
        updateProgress()

        // Animate pins entrance (stagger)
        animatePinsEntrance()
    }

    /** เช็คอินจุดปัจจุบัน */
    private fun checkInCurrentPin() {
        val cp = currentCheckpoint ?: return
        val key = "pin_${cp.number}"

        // ถ้าเช็คอินแล้ว ไม่ต้องทำอะไร
        if (prefs.getBoolean(key, false)) return

        // บันทึกลง SharedPreferences
        prefs.edit().putBoolean(key, true).apply()

        // เปลี่ยน pin เป็นสีทอง
        val pin = findViewById<ImageView>(cp.pinId)
        changePinToGold(pin)

        // อัปเดตปุ่มเป็น "เช็คอินแล้ว"
        showCheckedInButton()

        // Bounce animation ฉลอง
        celebratePin(pin)

        // อัปเดต progress
        updateProgress()

        // เช็คว่าครบหมดหรือยัง
        val count = getCheckedInCount()
        if (count >= 6) {
            // ครบ! ฉลอง 🎉
            infoCard.postDelayed({ showCelebration() }, 800)
        }
    }

    /** เปลี่ยน pin เป็นสีทอง + animation */
    private fun changePinToGold(pin: ImageView) {
        pin.animate()
            .scaleX(1.8f)
            .scaleY(1.8f)
            .setDuration(150)
            .withEndAction {
                pin.setImageResource(R.drawable.ic_pin_checked)
                pin.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .setInterpolator(OvershootInterpolator(4f))
                    .start()
            }
            .start()
    }

    /** Animation ฉลองเมื่อกดเช็คอิน */
    private fun celebratePin(pin: ImageView) {
        // สร้างดาวหลายดวง burst ออกจาก pin
        val parent = pin.parent as FrameLayout
        val colors = listOf("#FFD700", "#FFA000", "#FFEB3B", "#FF9800", "#4CAF50")
        val emojis = listOf("⭐", "✨", "🌟", "💫")

        repeat(8) { i ->
            val star = TextView(this)
            star.text = emojis[i % emojis.size]
            star.textSize = 18f
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            params.leftMargin = pin.left + pin.width / 2
            params.topMargin = pin.top + pin.height / 2
            parent.addView(star, params)

            val angle = (i * 45.0) * Math.PI / 180.0
            val distance = 120f + Random.nextFloat() * 80f

            val animX = ObjectAnimator.ofFloat(star, "translationX", 0f, (Math.cos(angle) * distance).toFloat())
            val animY = ObjectAnimator.ofFloat(star, "translationY", 0f, (Math.sin(angle) * distance).toFloat())
            val animAlpha = ObjectAnimator.ofFloat(star, "alpha", 1f, 0f)
            val animScale = ObjectAnimator.ofFloat(star, "scaleX", 0.5f, 1.5f)
            val animScaleY = ObjectAnimator.ofFloat(star, "scaleY", 0.5f, 1.5f)

            AnimatorSet().apply {
                playTogether(animX, animY, animAlpha, animScale, animScaleY)
                duration = 600 + (i * 50).toLong()
                startDelay = (i * 30).toLong()
                start()
            }

            star.postDelayed({ parent.removeView(star) }, 1200)
        }
    }

    /** อัปเดต progress bar และตัวเลข */
    private fun updateProgress() {
        val count = getCheckedInCount()
        stampProgress.progress = count
        txtProgressLabel.text = "เที่ยวแล้ว $count/6 จุด"
        txtPinCount.text = "$count/6"

        // เปลี่ยนสี label ตามความคืบหน้า
        if (count >= 6) {
            txtProgressLabel.text = "🎉 ครบทุกจุดแล้ว!"
            txtPinCount.text = "✅ 6/6"
        }
    }

    /** นับจำนวนที่เช็คอินแล้ว */
    private fun getCheckedInCount(): Int {
        return checkpoints.count { cp ->
            prefs.getBoolean("pin_${cp.number}", false)
        }
    }

    /** โหลดสถานะเช็คอินจาก SharedPreferences */
    private fun restoreCheckInState() {
        checkpoints.forEach { cp ->
            if (prefs.getBoolean("pin_${cp.number}", false)) {
                val pin = findViewById<ImageView>(cp.pinId)
                pin.setImageResource(R.drawable.ic_pin_checked)
            }
        }
    }

    /** แสดงปุ่มเช็คอินเป็นสถานะเช็คอินแล้ว */
    private fun showCheckedInButton() {
        btnCheckIn.text = "✅ เช็คอินแล้ว!"
        btnCheckIn.setBackgroundColor(Color.parseColor("#9E9E9E"))
        btnCheckIn.isEnabled = false
    }

    /** แสดงปุ่มเช็คอินปกติ */
    private fun showNormalCheckInButton() {
        btnCheckIn.text = "📍 เช็คอินที่นี่"
        btnCheckIn.setBackgroundResource(R.drawable.bg_checkin_button)
        btnCheckIn.isEnabled = true
    }

    /** ฉลองครบทุกจุด! 🎉 */
    private fun showCelebration() {
        hideInfoCard()

        val overlay = FrameLayout(this)
        overlay.setBackgroundColor(Color.parseColor("#CC000000"))
        val rootLayout = findViewById<FrameLayout>(R.id.main)
        rootLayout.addView(overlay, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))

        // ข้อความฉลอง
        val celebText = TextView(this)
        celebText.text = "🎉🏆🎉\n\nยินดีด้วย!\nคุณเที่ยวครบทุกจุดแล้ว!\n\nวัดสะพานสูง ขอขอบคุณ\nที่มาเยี่ยมชม 🙏"
        celebText.textSize = 22f
        celebText.setTextColor(Color.WHITE)
        celebText.gravity = Gravity.CENTER
        celebText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        overlay.addView(celebText, params)

        // Entrance animation
        celebText.alpha = 0f
        celebText.scaleX = 0.5f
        celebText.scaleY = 0.5f
        celebText.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setInterpolator(OvershootInterpolator(1.5f))
            .start()

        // สร้าง confetti
        spawnConfetti(overlay)

        // กดที่ไหนก็ได้เพื่อปิด
        overlay.setOnClickListener {
            overlay.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction { rootLayout.removeView(overlay) }
                .start()
        }
    }

    /** สร้างเอฟเฟค confetti */
    private fun spawnConfetti(parent: FrameLayout) {
        val emojis = listOf("🎊", "🎉", "⭐", "✨", "🌟", "💛", "🏆", "🎆")
        val screenWidth = resources.displayMetrics.widthPixels

        repeat(30) { i ->
            val confetti = TextView(this)
            confetti.text = emojis[i % emojis.size]
            confetti.textSize = (14 + Random.nextInt(14)).toFloat()
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            params.leftMargin = Random.nextInt(screenWidth)
            params.topMargin = -100
            parent.addView(confetti, params)

            confetti.alpha = 0f

            val fallDistance = (resources.displayMetrics.heightPixels + 200).toFloat()
            val animY = ObjectAnimator.ofFloat(confetti, "translationY", 0f, fallDistance)
            val animX = ObjectAnimator.ofFloat(confetti, "translationX", 0f, (Random.nextFloat() - 0.5f) * 200f)
            val animRotation = ObjectAnimator.ofFloat(confetti, "rotation", 0f, Random.nextFloat() * 720f)
            val animAlpha = ObjectAnimator.ofFloat(confetti, "alpha", 0f, 1f, 1f, 0.8f)

            AnimatorSet().apply {
                playTogether(animY, animX, animRotation, animAlpha)
                duration = 2000 + Random.nextLong(1500)
                startDelay = (i * 80).toLong()
                interpolator = AccelerateInterpolator(0.5f)
                start()
            }
        }
    }

    /** เพิ่ม pulse animation ให้ pin */
    private fun setupPinAnimation(pin: ImageView) {
        pin.alpha = 0f
    }

    /** pin ทั้งหมดโผล่ทีละตัว */
    private fun animatePinsEntrance() {
        checkpoints.forEachIndexed { index, cp ->
            val pin = findViewById<ImageView>(cp.pinId)
            pin.translationY = -30f
            pin.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay((index * 120 + 300).toLong())
                .setInterpolator(OvershootInterpolator(2f))
                .start()
        }
    }

    /** กด pin แล้ว bounce */
    private fun bouncePin(pin: ImageView) {
        pin.animate()
            .scaleX(1.5f)
            .scaleY(1.5f)
            .setDuration(120)
            .withEndAction {
                pin.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200)
                    .setInterpolator(OvershootInterpolator(3f))
                    .start()
            }
            .start()
    }

    /** แสดงข้อมูล checkpoint + เลื่อนไปที่ pin */
    private fun showCheckpointInfo(cp: CheckpointData, pin: ImageView) {
        currentCheckpoint = cp

        // ซ่อน hint
        txtHint.animate().alpha(0f).setDuration(200).start()

        // เลื่อน scroll ไปที่ pin
        val pinX = pin.left - hScroll.width / 2 + pin.width / 2
        val pinY = pin.top - vScroll.height / 2 + pin.height / 2
        hScroll.smoothScrollTo(pinX.coerceAtLeast(0), 0)
        vScroll.smoothScrollTo(0, pinY.coerceAtLeast(0))

        // อัปเดต info card
        infoBadge.text = cp.number
        infoTitle.text = cp.title
        infoDesc.text = cp.description
        infoHighlight.text = cp.highlight

        // อัปเดตปุ่มเช็คอิน
        val isCheckedIn = prefs.getBoolean("pin_${cp.number}", false)
        if (isCheckedIn) {
            showCheckedInButton()
        } else {
            showNormalCheckInButton()
        }

        // Slide up info card
        infoCard.visibility = View.VISIBLE
        infoCard.animate()
            .translationY(0f)
            .setDuration(350)
            .setInterpolator(OvershootInterpolator(0.8f))
            .start()
    }

    /** ซ่อน info card + แสดง hint กลับ */
    private fun hideInfoCard() {
        infoCard.animate()
            .translationY(600f)
            .setDuration(250)
            .withEndAction {
                infoCard.visibility = View.GONE
            }
            .start()

        txtHint.animate().alpha(1f).setDuration(300).setStartDelay(200).start()
    }
}