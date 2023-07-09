package com.gemastik.bersihkanbersama.ui.DonationPayment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.gemastik.bersihkanbersama.data.models.PaymentDetailsModel
import com.gemastik.bersihkanbersama.databinding.ActivityDonationPaymentBinding
import com.gemastik.bersihkanbersama.ui.viewmodels.DonationViewModel
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class DonationPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonationPaymentBinding
    private val viewModel: DonationViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

//    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityDonationPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        lifecycleScope.launch {
//            viewModel.getUserData().collect {
//                userData = it
//            }
//        }

        val id = intent.getStringExtra("id")

        Log.d("driskidebug", "onCreate: ${id}")

        getData(id.toString())

        val pullToRefresh = binding.swiperefresh
        pullToRefresh.setOnRefreshListener { // your code
            pullToRefresh.isRefreshing = false
            getData(id.toString())
        }


    }

    private fun getData(id: String) {

        if (id != "") {
            viewModel.getPaymentDetails(id).observe(this) {
                if (it != null) {
                    when (it) {
                        is Result.Loading -> {
                            loadingState(true)
                        }

                        is Result.Success -> {
                            loadingState(false)
                            assignData(it.data)
                        }

                        is Result.Error -> {
                            loadingState(false)
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(applicationContext, "Failed to parse result", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun assignData(paymentDetails: PaymentDetailsModel){
        binding.apply {
            if (paymentDetails.status.equals("Paid")){
                layoutWaitingPayment.visibility = View.GONE
                layoutPaymentSuccess.visibility = View.VISIBLE
                btnPayDetails.setOnClickListener{
                    val i =  Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(paymentDetails.paymentUrl))
                    it.context.startActivity(i)
                }
            }
            else {
//                tvpayAmount.text = "Rp ${paymentDetails.amount}"
                tvpayAmount.text = formatPrice(paymentDetails.amount)
                btnPayHere.setOnClickListener{
                    val i =  Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(paymentDetails.paymentUrl))
                    it.context.startActivity(i)
                }
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                layoutWaitingPayment.visibility = View.GONE
                paymentDetailsLoading.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                layoutWaitingPayment.visibility = View.VISIBLE
                paymentDetailsLoading.visibility = View.GONE
            }
        }
    }

    fun formatPrice(price: Double): String {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault()).format(price)
        return "Rp $formattedPrice"
    }
}