package com.gemastik.bersihkanbersama.ui.donate

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gemastik.bersihkanbersama.databinding.ActivityDonateBinding
import com.gemastik.bersihkanbersama.utils.Utils.formatPrice

class DonateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonateBinding
    private lateinit var donasiCardViews: List<DonasiCardView>
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        donasiCardViews = listOf(
            binding.donasiCardView1,
            binding.donasiCardView2,
            binding.donasiCardView3,
            binding.donasiCardView4,
            binding.donasiCardView5,
            binding.donasiCardView6
        )

        observeDonasiCardViews()
        calculateTotalPrice()
    }

    private fun observeDonasiCardViews() {
        donasiCardViews.forEach { donasiCardView ->
            donasiCardView.totalPrice.observe(this, Observer {
                calculateTotalPrice()
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTotalPrice() {
        totalPrice = donasiCardViews.sumOf { donasiCardView ->
            donasiCardView.totalPrice.value ?: 0
        }
        binding.textView26.text = formatPrice(totalPrice)
    }


}