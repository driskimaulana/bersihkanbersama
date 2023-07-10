package com.gemastik.bersihkanbersama.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.databinding.ActivityRegisterBinding
import com.gemastik.bersihkanbersama.ui.main.MainActivity
import com.gemastik.bersihkanbersama.ui.login.LoginActivity
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonRegister.setOnClickListener {
            val name = binding.textFieldName.editText?.text.toString()
            val phone = binding.textFieldPhone.editText?.text.toString()
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.customPasswordInput.getPassword()
            register(name, phone, email, password)
        }

        binding.masukButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register(name: String, phone: String, email: String, password: String) {
        viewModel.userSignUp(name, phone, email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)

                    val account = AccountModel(result.data.userId, result.data.token, "User")
                    viewModel.saveAccount(account)

                    startMainActivity()
                }

                is Result.Error -> {
                    Toast.makeText(this, "Login gagal. Periksa data anda. ${result.error}", Toast.LENGTH_SHORT)
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