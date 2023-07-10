package com.gemastik.bersihkanbersama.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.bersihkanbersama.data.models.TeamResultModel
import com.gemastik.bersihkanbersama.databinding.LeaderboardCardBinding

class LeaderboardListAdapter :
    ListAdapter<TeamResultModel, LeaderboardListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LeaderboardCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    class MyViewHolder(private val binding: LeaderboardCardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(result: TeamResultModel) {
            binding.apply {
                textView28.text = result.teamName
                textView29.text = "${result.trashResult} Kg"
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TeamResultModel> =
            object : DiffUtil.ItemCallback<TeamResultModel>() {
                override fun areItemsTheSame(
                    oldItem: TeamResultModel,
                    newItem: TeamResultModel
                ): Boolean {
                    return oldItem.teamName == newItem.teamName
                }

                override fun areContentsTheSame(
                    oldItem: TeamResultModel,
                    newItem: TeamResultModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}