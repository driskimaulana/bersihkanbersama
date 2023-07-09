package com.gemastik.bersihkanbersama.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityLoginBinding
import com.gemastik.bersihkanbersama.databinding.ActivityLoginOrgBinding
import com.gemastik.bersihkanbersama.ui.register.RegisterActivity
import com.gemastik.bersihkanbersama.ui.register.RegisterOrgActivity

class LoginOrgActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOrgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOrgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.daftarButton.setOnClickListener {
            val intent = Intent(this, RegisterOrgActivity::class.java)
            startActivity(intent)
        }
    }
}