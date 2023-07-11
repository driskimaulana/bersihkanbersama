package com.gemastik.bersihkanbersama.ui.detailaksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.data.models.UserRegisteredModel
import com.gemastik.bersihkanbersama.databinding.ActivityDetailAksiBinding
import com.gemastik.bersihkanbersama.ui.adapters.DonatorListAdapter
import com.gemastik.bersihkanbersama.ui.aksiberlangsung.AksiBerlangsungActivity
import com.gemastik.bersihkanbersama.ui.donate.DonateActivity
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class DetailAksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAksiBinding
    private val viewModel: DetailAksiViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val donatorAdapter = DonatorListAdapter()

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(ACTIVITY_EXTRA).toString()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = donatorAdapter

        viewModel.getActivityById(id!!).observe(this) {
            when (it) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)
                    buttonState(it.data.organizationId, id, it.data.volunteer.userRegistered)

                    Glide.with(binding.root.context)
                        .load(it.data.coverImage)
                        .placeholder(R.drawable.baseline_image_24)
                        .error(R.drawable.baseline_broken_image_24)
                        .centerCrop()
                        .into(binding.imageView)
                    var city = "Kota tidak diketahui"
                    if (it.data.location.city != "") {
                        city = it.data.location.city
                    }
                    binding.apply {
                        textView.text = it.data.title
                        textView2.text = city
                        textView3.text = "${it.data.volunteer.count} Mendaftar"
                        textView4.text = it.data.description
                        if (it.data.donationActivity.totalDonation == 0) {
                            textView8.text = "Aksi ini belum memiliki donatur"
                        } else {
                            donatorAdapter.submitList(it.data.donationActivity.donationHistory)
                        }
                    }
                }

                is Result.Error -> {
                    loadingState(false)
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun buttonState(id: String, idActivity: String, users: List<UserRegisteredModel>) {
        viewModel.getAccount().observe(this) {
            if (it.role == "Organization" && it.id == id) {
                binding.tvBantuDonasi.visibility = View.GONE
                startActivity(idActivity)
            } else {
                val user = users.find { data ->
                    data.id == it.id
                }
                if (user?.id == it.id) {
                    binding.daftarButton.visibility = View.GONE
                }
                signUpActivity(idActivity)
                startDonation()
            }
        }
    }

    private fun startActivity(id: String) {
        binding.daftarButton.text = "Mulai"
        binding.daftarButton.setOnClickListener {
            viewModel.getAccount().observe(this@DetailAksiActivity) {
                viewModel.startActivity(it.token, id).observe(this@DetailAksiActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            loadingState(true)
                        }

                        is Result.Success -> {
                            loadingState(false)
                            AlertDialog.Builder(this@DetailAksiActivity).apply {
                                setTitle("Sukses")
                                setMessage("Anda berhasil memulai kegiatan")
                                setPositiveButton("Ok") { _, _ ->
                                    val intent = Intent(this@DetailAksiActivity, AksiBerlangsungActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        is Result.Error -> {
                            loadingState(false)
                            Toast.makeText(this@DetailAksiActivity, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun signUpActivity(id: String) {
        binding.daftarButton.text = "Daftar"
        binding.daftarButton.setOnClickListener {
            viewModel.getAccount().observe(this@DetailAksiActivity) {
                viewModel.registerToActivity(it.token, id).observe(this@DetailAksiActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            loadingState(true)
                        }

                        is Result.Success -> {
                            loadingState(false)
                            AlertDialog.Builder(this@DetailAksiActivity).apply {
                                setTitle("Sukses")
                                setMessage("Anda berhasil mengikuti kegiatan")
                                setPositiveButton("Ok") { _, _ ->
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                                create()
                                show()
                            }
                        }

                        is Result.Error -> {
                            loadingState(false)
                            Toast.makeText(this@DetailAksiActivity, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun startDonation() {
        binding.tvBantuDonasi.setOnClickListener {
            val intent = Intent(this, DonateActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun loadingState(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                imageView2.visibility = View.GONE
                textView2.visibility = View.GONE
                imageView3.visibility = View.GONE
                textView3.visibility = View.GONE
                textView4.visibility = View.GONE
                viewProfile.visibility = View.GONE
                view.visibility = View.GONE
                textView8.visibility = View.GONE
                recyclerView.visibility = View.GONE
                daftarButton.visibility = View.GONE
                tvBantuDonasi.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                imageView2.visibility = View.VISIBLE
                textView2.visibility = View.VISIBLE
                imageView3.visibility = View.VISIBLE
                textView3.visibility = View.VISIBLE
                textView4.visibility = View.VISIBLE
                viewProfile.visibility = View.VISIBLE
                view.visibility = View.VISIBLE
                textView8.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
                daftarButton.visibility = View.VISIBLE
                viewModel.getAccount().observe(this@DetailAksiActivity) {
                    if (it.role == "User") {
                        tvBantuDonasi.visibility = View.VISIBLE
                    } else {
                        tvBantuDonasi.visibility = View.GONE
                    }
                }
            }
        }
    }

    companion object {
        const val ACTIVITY_EXTRA = "extra"
    }
}