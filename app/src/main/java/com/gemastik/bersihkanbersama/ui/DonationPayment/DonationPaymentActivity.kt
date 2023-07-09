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
import androidx.lifecycle.lifecycleScope
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityDonationPaymentBinding
import kotlinx.coroutines.launch

class DonationPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonationPaymentBinding
//    private val viewModel: OrderListViewModel by viewModels {
//        ViewModelFactory.getInstance(this)
//    }

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
            Log.d("driskidebug", "onCreate: Refresh")
        }


    }

    private fun getData(id: String) {

//        if (id != "") {
//            viewModel.getCustomerOrderPaymentDetails(userData.token, id.toInt()).observe(this) {
//                if (it != null) {
//                    when (it) {
//                        is Result.Loading -> {
//                            loadingState(true)
//                        }
//
//                        is Result.Success -> {
//                            loadingState(false)
//                            assignData(it.data)
//                            Log.d("driskidebug", "getData: $it")
//                        }
//
//                        is Result.Error -> {
//                            loadingState(false)
//                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        }
    }

//    private fun assignData(paymentDetails: PaymentDetailsModel){
//        binding.apply {
//            if (paymentDetails.status.equals("SETTLED")){
//                layoutWaitingPayment.visibility = View.GONE
//                layoutPaymentSuccess.visibility = View.VISIBLE
//                layoutPaymentExpired.visibility = View.GONE
//                btnPayDetails.setOnClickListener{
//                    Log.d("driskidebug", "assignData: Clicked")
//                    val i =  Intent(Intent.ACTION_VIEW)
//                    i.setData(Uri.parse(paymentDetails.url))
//                    it.context.startActivity(i)
//                }
//            }else if(paymentDetails.status.equals("EXPIRED")){
//                layoutWaitingPayment.visibility = View.GONE
//                layoutPaymentSuccess.visibility = View.GONE
//                layoutPaymentExpired.visibility = View.VISIBLE
//
//                btnBack.setOnClickListener{
//                    super.onBackPressed()
//                }
//            }
//            else {
//                tvpayAmount.text = "Rp ${paymentDetails.amount}"
//                btnPayHere.setOnClickListener{
//                    Log.d("driskidebug", "assignData: Clicked")
//                    val i =  Intent(Intent.ACTION_VIEW)
//                    i.setData(Uri.parse(paymentDetails.url))
//                    it.context.startActivity(i)
//                }
//            }
//        }
//    }

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
}