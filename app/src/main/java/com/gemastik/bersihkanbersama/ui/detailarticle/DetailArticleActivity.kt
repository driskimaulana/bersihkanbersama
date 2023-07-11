package com.gemastik.bersihkanbersama.ui.detailarticle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.ActivityDetailArticleBinding
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.Utils
import com.gemastik.bersihkanbersama.utils.ViewModelFactory
import java.util.TimeZone

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: DetailArticleViewModel by viewModels {
            ViewModelFactory.getInstance(this)
        }

        val id = intent.getStringExtra(ARTICLE_EXTRA)

        viewModel.getArticleById(id!!).observe(this) {
            when (it) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)
                    binding.apply {
                        Glide.with(root.context)
                            .load(it.data.cover)
                            .placeholder(R.drawable.baseline_image_24)
                            .error(R.drawable.baseline_broken_image_24)
                            .centerCrop()
                            .into(ivCover)

                        tvTitle.text = it.data.title
                        tvWriter.text = it.data.write
                        tvDate.text = Utils.formatDate(it.data.createdAt, TimeZone.getDefault().id)
                        tvContent.text = it.data.content
                    }
                }

                is Result.Error -> {
                    loadingState(false)
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
                ivCover.visibility = View.GONE
                tvTitle.visibility = View.GONE
                ivWriter.visibility = View.GONE
                tvWriter.visibility = View.GONE
                tvDate.visibility = View.GONE
                tvContent.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                ivCover.visibility = View.VISIBLE
                tvTitle.visibility = View.VISIBLE
                ivWriter.visibility = View.VISIBLE
                tvWriter.visibility = View.VISIBLE
                tvDate.visibility = View.VISIBLE
                tvContent.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val ARTICLE_EXTRA = "extra"
    }
}