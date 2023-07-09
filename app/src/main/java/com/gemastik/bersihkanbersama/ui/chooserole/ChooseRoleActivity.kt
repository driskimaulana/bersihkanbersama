package com.gemastik.bersihkanbersama.ui.chooserole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityChooseRoleBinding
import com.gemastik.bersihkanbersama.databinding.ActivityLoginBinding
import com.gemastik.bersihkanbersama.ui.login.LoginActivity
import com.gemastik.bersihkanbersama.ui.login.LoginOrgActivity
import com.gemastik.bersihkanbersama.ui.register.RegisterActivity

class ChooseRoleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardViewUser.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.cardViewOrg.setOnClickListener {
            val intent = Intent(this, LoginOrgActivity::class.java)
            startActivity(intent)
        }
    }
}