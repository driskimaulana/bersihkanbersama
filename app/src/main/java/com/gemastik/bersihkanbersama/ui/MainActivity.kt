package com.gemastik.bersihkanbersama.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityDonateBinding
import com.gemastik.bersihkanbersama.databinding.ActivityMainBinding
import com.gemastik.bersihkanbersama.ui.addAksi.AddAksiFragment
import com.gemastik.bersihkanbersama.ui.forum.home.ForumHomeFragment
import com.gemastik.bersihkanbersama.ui.home.HomeFragment
import com.gemastik.bersihkanbersama.ui.profile.ProfileFragment
import com.gemastik.bersihkanbersama.ui.shop.home.ShopHomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.realBottomNavbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.chat -> replaceFragment(ForumHomeFragment())
                R.id.shop -> replaceFragment(ShopHomeFragment())
//                R.id.shop -> replaceFragment(AddAksiFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainScreenFragment, fragment)
        fragmentTransaction.commit()

    }
}