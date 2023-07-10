package com.gemastik.bersihkanbersama.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.data.models.ActivityModel
import com.gemastik.bersihkanbersama.databinding.AksiCardBinding
import com.gemastik.bersihkanbersama.ui.aksiberlangsung.AksiBerlangsungActivity
import com.gemastik.bersihkanbersama.ui.detailaksi.DetailAksiActivity

class ActivityListAdapter :
    ListAdapter<ActivityModel, ActivityListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = AksiCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val activity = getItem(position)
        holder.bind(activity)
    }

    class MyViewHolder(private val binding: AksiCardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(activity: ActivityModel) {
            Glide.with(itemView.context)
                .load(activity.coverImage)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_broken_image_24)
                .centerCrop()
                .into(binding.imageView9)
            binding.apply {
                var city = "Kota tidak diketahui"
                if (activity.location.city != "") {
                    city = activity.location.city
                }
                textView22.text = activity.title
                textView23.text = activity.description
                textView2.text = city
                textView3.text = "${activity.volunteer.count} Mendaftar"
            }
            itemView.setOnClickListener {
                if (activity.status == "Not Started") {
                    val intent = Intent(itemView.context, DetailAksiActivity::class.java)
                    intent.putExtra(DetailAksiActivity.ACTIVITY_EXTRA, activity.id)
                    itemView.context.startActivity(intent)
                } else {
                    val intent = Intent(itemView.context, AksiBerlangsungActivity::class.java)
                    intent.putExtra(AksiBerlangsungActivity.ACTIVITY_EXTRA, activity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ActivityModel> =
            object : DiffUtil.ItemCallback<ActivityModel>() {
                override fun areItemsTheSame(
                    oldItem: ActivityModel,
                    newItem: ActivityModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ActivityModel,
                    newItem: ActivityModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}