package com.gemastik.bersihkanbersama.ui.aksiberlangsung

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityAksiBerlangsungBinding
import com.gemastik.bersihkanbersama.databinding.ActivityDonateBinding
import com.gemastik.bersihkanbersama.ui.addhasilkilo.AddHasilKiloActivity
import com.gemastik.bersihkanbersama.ui.detailaksi.DetailAksiActivity
import com.gemastik.bersihkanbersama.ui.register.RegisterActivity

class AksiBerlangsungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAksiBerlangsungBinding

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // RENDER ULANG
                Toast.makeText(this, "Sukses menambahkan", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksiBerlangsungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(DetailAksiActivity.ACTIVITY_EXTRA)

        binding.tambahHasil.setOnClickListener {
            val intent = Intent(this, AddHasilKiloActivity::class.java)
            intent.putExtra("id", "64a911e58c3a2a2d718716f1")
            resultLauncher.launch(intent)
        }
    }

    companion object {
        const val ACTIVITY_EXTRA = "extra"
    }
}