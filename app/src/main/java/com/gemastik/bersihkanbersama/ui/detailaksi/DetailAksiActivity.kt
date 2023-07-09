package com.gemastik.bersihkanbersama.ui.detailaksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gemastik.bersihkanbersama.databinding.ActivityDetailAksiBinding
import com.gemastik.bersihkanbersama.ui.donate.DonateActivity

class DetailAksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBantuDonasi.setOnClickListener {
            val intent = Intent(this, DonateActivity::class.java)
            startActivity(intent)
        }
    }
}