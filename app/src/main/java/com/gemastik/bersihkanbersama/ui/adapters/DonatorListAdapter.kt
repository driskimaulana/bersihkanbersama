package com.gemastik.bersihkanbersama.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.bersihkanbersama.data.models.DonationModel
import com.gemastik.bersihkanbersama.databinding.DonatorCardBinding
import com.gemastik.bersihkanbersama.utils.Utils

class DonatorListAdapter :
    ListAdapter<DonationModel, DonatorListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DonatorCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val donator = getItem(position)
        holder.bind(donator)
    }

    class MyViewHolder(private val binding: DonatorCardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(donator: DonationModel) {
            binding.apply {
                textView5.text = donator.userName
                textView7.text = donator.totalDonation?.let { Utils.formatPrice(it) }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DonationModel> =
            object : DiffUtil.ItemCallback<DonationModel>() {
                override fun areItemsTheSame(
                    oldItem: DonationModel,
                    newItem: DonationModel
                ): Boolean {
                    return oldItem.donationId == newItem.donationId
                }

                override fun areContentsTheSame(
                    oldItem: DonationModel,
                    newItem: DonationModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}