package com.n.crypt.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.n.crypt.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startApplication()
        }, 1800)
    }

    private fun startApplication() {
        startActivity(
            Intent(
                this@SplashActivity,
                LoginActivity::class.java
            )
        )
    }
}
