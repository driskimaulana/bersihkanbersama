package com.gemastik.bersihkanbersama.ui.aksiberlangsung

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.result.contract.ActivityResultContracts
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityAksiBerlangsungBinding
import com.gemastik.bersihkanbersama.ui.adapters.LeaderboardListAdapter
import com.gemastik.bersihkanbersama.ui.addhasilkilo.AddHasilKiloActivity
import com.gemastik.bersihkanbersama.ui.detailaksi.DetailAksiActivity
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class AksiBerlangsungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAksiBerlangsungBinding
    private val viewModel: AksiBerlangsungViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val adapter = LeaderboardListAdapter()

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // RENDER ULANG
                initialView()
                Toast.makeText(this, "Sukses menambahkan", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksiBerlangsungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(DetailAksiActivity.ACTIVITY_EXTRA).toString()

        initialView()

    }

    private fun initialView(){

        val layoutManager = LinearLayoutManager(this)
        binding.rvLeaderboard.layoutManager = layoutManager
        binding.rvLeaderboard.setHasFixedSize(true)
        binding.rvLeaderboard.adapter = adapter

        viewModel.getActivityById(id!!).observe(this) {
            when (it) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)

                    binding.apply {
                        var city = "Kota tidak diketahui"
                        if (it.data.location.city != "") {
                            city = it.data.location.city
                        }
                        textView24.text = it.data.title
                        textView4.text = city
                        textView3.text = "${it.data.volunteer.count} Mendaftar"
                        var team = "Anda tidak terdaftar"
                        for (i in it.data.volunteer.teams) {
                            for (j in i.members) {
                                viewModel.getAccount()
                                    .observe(this@AksiBerlangsungActivity) { account ->
                                        if (account.id == j.id) {
                                            team = i.name
                                        }
                                    }
                            }
                        }
                        textView27.text = team
                        textView33.text = it.data.rewards.second.toString()
                        textView32.text = it.data.rewards.first.toString()
                        textView34.text = it.data.rewards.third.toString()
                        if (it.data.volunteer.teams.isEmpty()) {
                            textView30.text = "Belum ada team yang terdaftar"
                            rvLeaderboard.visibility = View.GONE
                        } else {
                            viewModel.getLeaderboard(it.data.id)
                                .observe(this@AksiBerlangsungActivity) { result ->
                                    when (result) {
                                        is Result.Loading -> {
                                        }

                                        is Result.Success -> {
                                            adapter.submitList(result.data.leaderboard)
                                        }

                                        is Result.Error -> {
                                            Toast.makeText(
                                                this@AksiBerlangsungActivity,
                                                result.error,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                        }

                        viewModel.getAccount().observe(this@AksiBerlangsungActivity) { account ->
                            if (account.id != it.data.organizationId || it.data.status == "Finished") {
                                btnFinish.visibility = View.GONE
                                tambahHasil.visibility = View.GONE
                            } else {
                                cvContainer.visibility = View.GONE
                                tambahHasilButtonAction()
                                finishButtonAction(account.token, it.data.id)
                            }
                        }
                    }
                }

                is Result.Error -> {
                    loadingState(false)
                    Toast.makeText(this@AksiBerlangsungActivity, it.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun tambahHasilButtonAction() {
        binding.tambahHasil.setOnClickListener {
            val intent = Intent(this, AddHasilKiloActivity::class.java)
            intent.putExtra("id", id)
            resultLauncher.launch(intent)
        }
    }

    private fun finishButtonAction(token: String, id: String) {
        binding.btnFinish.setOnClickListener {
            viewModel.finishActivity(token, id).observe(this@AksiBerlangsungActivity) {
                when (it) {
                    is Result.Loading -> {
                        loadingState(true)
                    }

                    is Result.Success -> {
                        loadingState(false)

                        AlertDialog.Builder(this@AksiBerlangsungActivity).apply {
                            setTitle("Sukses")
                            setMessage("Kegiatan ini telah selesai!")
                            setPositiveButton("Ok") { _, _ ->
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            }
                            create()
                            show()
                        }
                    }

                    is Result.Error -> {
                        loadingState(false)
                        Toast.makeText(this@AksiBerlangsungActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
                textView24.visibility = View.GONE
                imageView2.visibility = View.GONE
                textView2.visibility = View.GONE
                imageView4.visibility = View.GONE
                textView4.visibility = View.GONE
                imageView3.visibility = View.GONE
                textView3.visibility = View.GONE
                cvContainer.visibility = View.GONE
                textView31.visibility = View.GONE
                standingLeaderboardPoint.visibility = View.GONE
                textView30.visibility = View.GONE
                rvLeaderboard.visibility = View.GONE
                containerBtn.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                textView24.visibility = View.VISIBLE
                imageView2.visibility = View.VISIBLE
                textView2.visibility = View.VISIBLE
                imageView4.visibility = View.VISIBLE
                textView4.visibility = View.VISIBLE
                imageView3.visibility = View.VISIBLE
                textView3.visibility = View.VISIBLE
                cvContainer.visibility = View.VISIBLE
                textView31.visibility = View.VISIBLE
                standingLeaderboardPoint.visibility = View.VISIBLE
                textView30.visibility = View.VISIBLE
                rvLeaderboard.visibility = View.VISIBLE
                containerBtn.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val ACTIVITY_EXTRA = "extra"
    }
}