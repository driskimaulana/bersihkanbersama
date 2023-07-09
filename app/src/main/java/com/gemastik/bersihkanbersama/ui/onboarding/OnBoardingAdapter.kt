package com.gemastik.bersihkanbersama.ui.onboarding

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.bersihkanbersama.R

class OnBoardingAdapter(private val context: Context, private val onBoardingItems: List<OnBoardingItem>):
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tv_onboarding_description)
        val imageOnboarding: ImageView = itemView.findViewById(R.id.imageView_onboarding_background)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_container_onboarding, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnBoardingAdapter.ViewHolder, position: Int) {
        val item = onBoardingItems[position]
        holder.textTitle.text = item.description
        holder.imageOnboarding.setImageResource(item.image)
//
//        holder.btnGabungSekarang.setOnClickListener {
//            val intent = Intent(context, LoginActivity::class.java)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }


}