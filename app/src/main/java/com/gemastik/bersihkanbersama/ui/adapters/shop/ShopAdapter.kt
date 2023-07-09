package com.gemastik.bersihkanbersama.ui.adapters.shop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.ui.onboarding.OnBoardingItem

class ShopAdapter(private val context: Context, private val shopItems: List<ShopItem>):
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val coverImg: ImageView = itemView.findViewById(R.id.iv_cover)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopAdapter.ViewHolder, position: Int) {
        val item = shopItems[position]
        holder.title.text = item.title
        holder.price.text = "${item.point.toString()} Point"
        holder.coverImg.setImageResource(item.image)
//
//        holder.btnGabungSekarang.setOnClickListener {
//            val intent = Intent(context, LoginActivity::class.java)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }


}