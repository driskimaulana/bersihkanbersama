package com.gemastik.bersihkanbersama.ui.aksiberlangsung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityAksiBerlangsungBinding
import com.gemastik.bersihkanbersama.databinding.ActivityDonateBinding
import com.gemastik.bersihkanbersama.ui.addhasilkilo.AddHasilKiloActivity
import com.gemastik.bersihkanbersama.ui.detailaksi.DetailAksiActivity
import com.gemastik.bersihkanbersama.ui.register.RegisterActivity

class AksiBerlangsungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAksiBerlangsungBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksiBerlangsungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(DetailAksiActivity.ACTIVITY_EXTRA)

        binding.tambahHasil.setOnClickListener {
            val intent = Intent(this, AddHasilKiloActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val ACTIVITY_EXTRA = "extra"
    }
}