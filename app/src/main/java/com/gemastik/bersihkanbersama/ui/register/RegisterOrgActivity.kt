package com.gemastik.bersihkanbersama.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.data.remote.request.ContactRequest
import com.gemastik.bersihkanbersama.data.remote.request.OrganizationSignUpRequest
import com.gemastik.bersihkanbersama.databinding.ActivityRegisterOrgBinding
import com.gemastik.bersihkanbersama.ui.MainActivity
import com.gemastik.bersihkanbersama.ui.login.LoginActivity
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.Utils.daftarKota
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class RegisterOrgActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterOrgBinding
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var kota: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOrgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDropdownInputItemList()
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonRegister.setOnClickListener {
            val name = binding.textFieldName.editText?.text.toString()
            val description = binding.textFieldDescription.editText?.text.toString()
            val instagram = binding.textFieldInstagram.editText?.text.toString()
            val namaKontak = binding.textFieldContactName.editText?.text.toString()
            val phoneKontak = binding.textFieldContactPhone.editText?.text.toString()
            val email = binding.textFieldEmail.editText?.text.toString()
            val password = binding.customPasswordInput.getPassword()

            register(name, description, email, password, kota, instagram, namaKontak, phoneKontak)
        }

        binding.masukButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register(
        name: String,
        description: String,
        email: String,
        password: String,
        city: String,
        instagram: String,
        contactName: String,
        contactPhone: String
    ) {
        val contact = ContactRequest(contactName, contactPhone)
        val signupOrg =
            OrganizationSignUpRequest(name, description, email, password, city, instagram, contact)

        viewModel.organizationSignUp(signupOrg).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)

                    Log.d("DEBUGNOVAL", result.data.toString())

                    val account =
                        AccountModel(result.data.organizationId, result.data.token, "Organization")
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

    private fun initDropdownInputItemList() {
        val items = daftarKota
        val autoComplete: AutoCompleteTextView = binding.dropdownInput
        val adapter = ArrayAdapter(this, R.layout.list_dropdown, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                kota = items[position]
            }
    }

    private fun loadingState(isLoading: Boolean) {
        binding.submitTextview.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.submitLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}