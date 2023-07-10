package com.gemastik.bersihkanbersama.ui.leaderboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.bersihkanbersama.data.models.LeaderboardUserModel
import com.gemastik.bersihkanbersama.databinding.LeaderboardCardInFragmentBinding

class LeaderboardAdapter(private val userList: List<LeaderboardUserModel>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LeaderboardCardInFragmentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, position + 1)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(private val binding: LeaderboardCardInFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: LeaderboardUserModel, rank: Int) {
            binding.textView39.text = rank.toString()
            binding.textView28.text = user.name
            binding.textView29.text = "${user.point} Pt"
        }
    }
}