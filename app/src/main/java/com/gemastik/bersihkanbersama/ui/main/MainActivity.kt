package com.gemastik.bersihkanbersama.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityMainBinding
import com.gemastik.bersihkanbersama.ui.addAksi.AddAksiFragment
import com.gemastik.bersihkanbersama.ui.forum.home.ForumHomeFragment
import com.gemastik.bersihkanbersama.ui.home.HomeFragment
import com.gemastik.bersihkanbersama.ui.leaderboard.LeaderboardFragment
import com.gemastik.bersihkanbersama.ui.profile.ProfileFragment
import com.gemastik.bersihkanbersama.ui.shop.home.ShopHomeFragment
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        viewModel.account.observe(this) {
            binding.realBottomNavbarOrg.visibility = if (it.role == "User") View.INVISIBLE else View.VISIBLE
            binding.realBottomNavbar.visibility = if (it.role == "User") View.VISIBLE else View.INVISIBLE
        }

        binding.realBottomNavbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.chat -> replaceFragment(ForumHomeFragment())
                R.id.shop -> replaceFragment(ShopHomeFragment())
                R.id.medal -> replaceFragment(LeaderboardFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

        binding.realBottomNavbarOrg.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.chat -> replaceFragment(ForumHomeFragment())
                R.id.action -> replaceFragment(AddAksiFragment())
                R.id.medal -> replaceFragment(LeaderboardFragment())
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