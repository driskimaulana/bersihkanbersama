package com.gemastik.bersihkanbersama.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.databinding.ActivityLoginOrgBinding
import com.gemastik.bersihkanbersama.ui.main.MainActivity
import com.gemastik.bersihkanbersama.ui.register.RegisterOrgActivity
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class LoginOrgActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOrgBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOrgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonMasuk.setOnClickListener {
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.customPasswordInput.getPassword()
            login(email, password)
        }

        binding.daftarButton.setOnClickListener {
            val intent = Intent(this, RegisterOrgActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String) {
        viewModel.organizationSignIn(email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)

                    val account = AccountModel(result.data.id, result.data.name, result.data.token, result.data.role)

                    viewModel.saveAccount(account)

                    startMainActivity()
                }

                is Result.Error -> {
                    Toast.makeText(this, "Login gagal. Periksa data anda.", Toast.LENGTH_SHORT)
                        .show()
                    loadingState(false)
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadingState(isLoading: Boolean) {
        binding.submitTextview.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.submitLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}