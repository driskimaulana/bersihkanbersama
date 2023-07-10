package com.gemastik.bersihkanbersama.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.data.models.ArticleModel
import com.gemastik.bersihkanbersama.databinding.ItemArticleCardBinding
import com.gemastik.bersihkanbersama.ui.detailarticle.DetailArticleActivity

class ArticleListAdapter :
    ListAdapter<ArticleModel, ArticleListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class MyViewHolder(private val binding: ItemArticleCardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(article: ArticleModel) {
            Glide.with(itemView.context)
                .load(article.cover)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_broken_image_24)
                .centerCrop()
                .into(binding.ivCover)

            binding.apply {
                textView22.text = article.title
                textView23.text = article.summary
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.ARTICLE_EXTRA, article.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticleModel> =
            object : DiffUtil.ItemCallback<ArticleModel>() {
                override fun areItemsTheSame(
                    oldItem: ArticleModel,
                    newItem: ArticleModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ArticleModel,
                    newItem: ArticleModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}