package com.gemastik.bersihkanbersama.ui.donate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.data.models.DonationItem
import com.gemastik.bersihkanbersama.data.remote.request.DonationItemRequest
import com.gemastik.bersihkanbersama.data.remote.request.DonationRequest
import com.gemastik.bersihkanbersama.databinding.ActivityDonateBinding
import com.gemastik.bersihkanbersama.ui.DonationPayment.DonationPaymentActivity
import com.gemastik.bersihkanbersama.ui.viewmodels.DonationViewModel
import com.gemastik.bersihkanbersama.utils.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.launch

class DonateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonateBinding
    private lateinit var donasiCardViews: List<DonasiCardView>
    private var totalPrice = 0

    private var items: List<DonationItem> = listOf(
        DonationItem("Air Mineral", 0, 10000.0),
        DonationItem("Sarung Tangan", 0, 20000.0),
        DonationItem("Plastik Sampah", 0, 10000.0),
        DonationItem("Nasi Kotak", 0, 10000.0),
        DonationItem("Truk Sampah", 0, 300000.0),
        DonationItem("Masker", 0, 20000.0),
        DonationItem("Donasi Lain", 0, 10000.0),
    )

    private val viewModel: DonationViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var user: AccountModel

    private lateinit var activityId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getAccount().collect{
                user = it
            }
        }

        activityId = intent.getStringExtra("id") ?: ""

        donasiCardViews = listOf(
            binding.donasiCardView1,
            binding.donasiCardView2,
            binding.donasiCardView3,
            binding.donasiCardView4,
            binding.donasiCardView5,
            binding.donasiCardView6
        )

        binding.edtBiayalain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text is being changed.
                if (count == 0){
                    totalPrice = 0
                    binding.textView26.text = formatPrice(totalPrice)
                    return
                }
                totalPrice = s.toString().toInt()
                binding.textView26.text = formatPrice(totalPrice)
                // Perform any desired operations with the new text
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has been changed.

            }
        })

        observeDonasiCardViews()
        calculateTotalPrice()

        binding.bayarButton.setOnClickListener(View.OnClickListener {
            if (totalPrice != 0){
                proceedToPayment()
            }else {
                Log.d("driskidebug", user.toString())
                Toast.makeText(applicationContext, "Masukkan donasi anda.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun proceedToPayment() {
        var donationItem = listOf<DonationItemRequest>(
            DonationItemRequest("Donasi Lain", 1, binding.edtBiayalain.text.toString().toDouble())
        )
        var donationBody = DonationRequest(donationItem, binding.checkBox.isChecked)

        viewModel.createNewDonation(
            user.token,
                    activityId,
                    donationBody
            ).observe(this) {
            if (it != null) {
                when (it) {
                    is com.gemastik.bersihkanbersama.utils.Result.Loading -> {
                        loadingState(true)
                    }
                    is com.gemastik.bersihkanbersama.utils.Result.Success -> {
                        loadingState(false)
                        val intent = Intent(this, DonationPaymentActivity::class.java)
                        intent.putExtra("id", it.data)
                        startActivity(intent)
                        super.onBackPressed()
                    }
                    is com.gemastik.bersihkanbersama.utils.Result.Error -> {
                        loadingState(false)
                        Toast.makeText(this, "ERRORR", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        Log.d("driskidebug", "Error")
                    }
                }
            }
        }

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
        binding.edtBiayalain.setText("0")
        totalPrice = donasiCardViews.sumOf { donasiCardView ->
            donasiCardView.totalPrice.value ?: 0
        }

        binding.textView26.text = formatPrice(totalPrice)
    }

    fun formatPrice(price: Int): String {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault()).format(price)
        return "Rp $formattedPrice"
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                bayarButton.visibility = View.INVISIBLE
                submitLoading.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                bayarButton.visibility = View.VISIBLE
                submitLoading.visibility = View.GONE
            }
        }
    }
}
