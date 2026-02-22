package com.example.templesp

import android.os.Bundle

class SocialActivity : BaseNavActivity() {

    override fun getCurrentIconRes(): Int = R.drawable.ic_social_group
    override fun getCurrentNavId(): Int = R.id.navIconSocial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)
        setupPillNav()
    }
}