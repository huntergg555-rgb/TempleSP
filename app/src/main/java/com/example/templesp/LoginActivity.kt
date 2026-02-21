package com.example.templesp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.button.MaterialButton
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    // ตัวแปรของ Facebook และ LINE
    private lateinit var callbackManager: CallbackManager
    private val lineChannelId = "2009191016"

    // ตัวรับผลลัพธ์จากการล็อกอิน LINE
    private val lineLoginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val loginResult = LineLoginApi.getLoginResultFromIntent(data)

            when (loginResult.responseCode) {
                LineApiResponseCode.SUCCESS -> {
                    val profile = loginResult.lineProfile
                    Toast.makeText(this, "LINE สำเร็จ! สวัสดีคุณ ${profile?.displayName}", Toast.LENGTH_LONG).show()

                    // ล็อกอิน LINE สำเร็จ ให้ไปหน้า MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                LineApiResponseCode.CANCEL -> Toast.makeText(this, "ยกเลิกการล็อกอิน LINE", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "เกิดข้อผิดพลาด: ${loginResult.errorData.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ==========================================
        // 1. ผูกปุ่มและช่องกรอกข้อมูล (จากโค้ดเดิมของคุณ)
        // ==========================================
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val btnRegister = findViewById<MaterialButton>(R.id.btnRegister)

        val btnFacebook = findViewById<CardView>(R.id.btnFacebook)
        val btnLine = findViewById<CardView>(R.id.btnLine)

        // ==========================================
        // 2. ปุ่มเข้าสู่ระบบแบบปกติ (admin / 1234)
        // ==========================================
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

        // ==========================================
        // 3. ปุ่มลงทะเบียน
        // ==========================================
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // ==========================================
        // 4. ปุ่มล็อกอิน Facebook (ระบบจริง)
        // ==========================================
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Toast.makeText(this@LoginActivity, "Facebook ล็อกอินสำเร็จ!", Toast.LENGTH_LONG).show()
                    // ล็อกอิน Facebook สำเร็จ ให้ไปหน้า MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "ผู้ใช้ยกเลิกการล็อกอิน", Toast.LENGTH_SHORT).show()
                }
                override fun onError(error: FacebookException) {
                    Toast.makeText(this@LoginActivity, "เกิดข้อผิดพลาด: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

        btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        }

        // ==========================================
        // 5. ปุ่มล็อกอิน LINE (ระบบจริง)
        // ==========================================
        btnLine.setOnClickListener {
            try {
                val loginIntent = LineLoginApi.getLoginIntent(
                    this,
                    lineChannelId,
                    LineAuthenticationParams.Builder()
                        .scopes(listOf(Scope.PROFILE, Scope.OPENID_CONNECT, Scope.OC_EMAIL))
                        .build()
                )
                lineLoginLauncher.launch(loginIntent)
            } catch (e: Exception) {
                Log.e("LINE_LOGIN", e.toString())
            }
        }
    }

    // ฟังก์ชันรับผลลัพธ์ของ Facebook
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}