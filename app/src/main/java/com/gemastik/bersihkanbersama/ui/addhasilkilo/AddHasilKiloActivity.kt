package com.gemastik.bersihkanbersama.ui.addhasilkilo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.databinding.ActivityAddHasilKiloBinding
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.Utils.daftarTim
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class AddHasilKiloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHasilKiloBinding
    private val viewModel: AddHasilKiloViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var tim: String
    private lateinit var activityId: String
    private lateinit var account: AccountModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHasilKiloBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.account.observe(this) {
            account = it
        }

        activityId = intent.getStringExtra("id") ?: "64a911e58c3a2a2d718716f1"

        initDropdownInputItemList()

        binding.tambahkanButton.setOnClickListener {
            val hasil = binding.textFieldHasilKilo.editText?.text.toString().toDouble()
            tambahkan(account.token, activityId, tim, hasil)
        }
    }

    private fun tambahkan(token: String, activityId: String, teamName: String, result: Double) {
        viewModel.addTeamResults(token, activityId, teamName, result).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)
                    finishActivity()
                }

                is Result.Error -> {
                    Toast.makeText(
                        this,
                        "Tambahkan hasil gagal. Periksa data anda.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    loadingState(false)
                }
            }
        }
    }

    private fun initDropdownInputItemList() {
        val items = daftarTim
        val autoComplete: AutoCompleteTextView = binding.dropdownInput
        val adapter = ArrayAdapter(this, R.layout.list_dropdown, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                tim = items[position]
            }
    }

    private fun finishActivity() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun loadingState(isLoading: Boolean) {
        binding.submitTextview.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.submitLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}