package com.gemastik.bersihkanbersama.ui.adapters.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.ui.adapters.shop.ShopItem

class ForumAdapter(private val context: Context, private val forums: List<ForumItem>):
    RecyclerView.Adapter<ForumAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val location: TextView = itemView.findViewById(R.id.tv_location_name)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val profileImage: ImageView = itemView.findViewById(R.id.iv_profile_image)
        val caption: TextView = itemView.findViewById(R.id.tv_title_caption)
        val contentPicture: ImageView = itemView.findViewById(R.id.iv_content_picture)
        val voteAmount: TextView = itemView.findViewById(R.id.tv_vote_amount)
        val commentAmount: Button = itemView.findViewById(R.id.btn_comment)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feed_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumAdapter.ViewHolder, position: Int) {
        val item = forums[position]
        holder.name.text = item.name
        holder.location.text = item.location
        holder.time.text = item.time
        holder.profileImage.setImageResource(item.profileImage)
        holder.caption.text = item.caption
        holder.contentPicture.setImageResource(item.postImage)
        holder.voteAmount.text = item.voteCount.toString()
        holder.commentAmount.text = item.commentCount.toString()
    }

    override fun getItemCount(): Int {
        return forums.size
    }


}