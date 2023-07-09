package com.gemastik.bersihkanbersama.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gemastik.bersihkanbersama.databinding.ActivityRegisterOrgBinding
import com.gemastik.bersihkanbersama.ui.login.LoginOrgActivity

class RegisterOrgActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterOrgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOrgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.masukButton.setOnClickListener {
            val intent = Intent(this, LoginOrgActivity::class.java)
            startActivity(intent)
        }
    }
}