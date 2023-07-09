package com.gemastik.bersihkanbersama.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.ui.chooserole.ChooseRoleActivity
import com.gemastik.bersihkanbersama.ui.donate.DonateActivity
import com.gemastik.bersihkanbersama.ui.login.LoginActivity
import com.gemastik.bersihkanbersama.ui.login.LoginOrgActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}