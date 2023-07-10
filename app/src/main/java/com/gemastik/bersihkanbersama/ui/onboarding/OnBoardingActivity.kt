package com.gemastik.bersihkanbersama.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityOnboardingBinding
import com.gemastik.bersihkanbersama.ui.chooserole.ChooseRoleActivity
import com.gemastik.bersihkanbersama.ui.main.MainActivity
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter
    private lateinit var indicatorlayout: LinearLayout
    private val viewModel: OnboardingViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAccount().observe(this@OnBoardingActivity) {
            if (it.id != "") {
                startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            }
        }

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val onboardingItems = listOf<OnBoardingItem>(
            OnBoardingItem(R.drawable.onboarding1,"Ikuti aksi bersih-bersih sampah di sekitarmu"),
            OnBoardingItem(R.drawable.onboarding2,"Kumpulkan poin dari mengikuti aksi dan tukarkan dengan barang menarik"),
            OnBoardingItem(R.drawable.onboarding3, "Bertemu dengan orang baru dan perluas jaringan pertemanan"),
            OnBoardingItem(R.drawable.onboarding4, "Jadilah bagian dari komunitas Trash Hero Indonesia")
        )

        onBoardingAdapter = OnBoardingAdapter(this, onboardingItems)

        val onboardingViewPager = binding.onboardingViewPager
        onboardingViewPager.adapter = onBoardingAdapter

        indicatorlayout = binding.layoutOnboardingIndicators

        setupOnBoardingIndicators()

        onboardingViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (binding.onboardingViewPager.currentItem == 3) {
                    binding.btnOnBoardingAction.text = "Gabung"
                    binding.btnOnBoardingAction.setOnClickListener {
                        startChooseRoleActivity()
                    }
                }else{
                    binding.btnOnBoardingAction.text = "Next"
                    binding.btnOnBoardingAction.setOnClickListener {
                        val currentItem = binding.onboardingViewPager.currentItem
                        if (currentItem < 3){
                            binding.btnOnBoardingAction.visibility = View.VISIBLE
                            setCurrenctOnBoardingIndicator(currentItem +1)
                            binding.onboardingViewPager.setCurrentItem(currentItem + 1)
                        }
                    }
                }
                super.onPageSelected(position)
                setCurrenctOnBoardingIndicator(position)
            }
        })



    }

    private fun startChooseRoleActivity(){
        val intent = Intent(this, ChooseRoleActivity::class.java)
//        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setupOnBoardingIndicators(){
        var indicators: ArrayList<ImageView> = arrayListOf<ImageView>()
        var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in 0..3){
            indicators.add(ImageView(applicationContext))
            indicators[i].setImageDrawable(
                ContextCompat
                    .getDrawable(applicationContext, R.drawable.onboarding_indicator_inactive))
            indicators[i].layoutParams = layoutParams
            indicatorlayout.addView(indicators[i])
        }
    }

    private fun setCurrenctOnBoardingIndicator(index: Int){
        var childCount: Int = indicatorlayout.childCount
        for (i in 0..3){
            var imageView: ImageView = indicatorlayout.getChildAt(i) as ImageView
            Log.d("driskidebug", "setupOnBoardingIndicators: hello ${imageView.toString()}")
            if (i == index){
                Log.d("driskidebug", "setupOnBoardingIndicators: hello2 ${imageView.toString()}")
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.onboarding_indicator_active)
                )
            }else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.onboarding_indicator_inactive)
                )
            }
        }
    }
}