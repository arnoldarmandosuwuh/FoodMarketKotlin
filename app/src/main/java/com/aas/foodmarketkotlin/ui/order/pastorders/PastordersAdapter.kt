package com.aas.foodmarketkotlin.ui.order.pastorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aas.foodmarketkotlin.R
import com.aas.foodmarketkotlin.model.response.transaction.Data
import com.aas.foodmarketkotlin.utils.Helpers.convertLongToTime
import com.aas.foodmarketkotlin.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_pastorders.view.*

class PastordersAdapter (
    private val listData: List<Data>,
    private val itemAdapterCallback: ItemAdapterCallback
    ) : RecyclerView.Adapter<PastordersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastordersAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_pastorders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PastordersAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tvTitle.text = data.food.name
                tvPrice.formatPrice(data.food.price.toString())
                tvDate.text = data.createdAt.convertLongToTime("MMM dd , HH.mm")

                Glide.with(context)
                    .load(data.food.picturePath)
                    .into(ivPoster)

                if (data.status.equals("CANCELLED", true)){
                    tvStatus.visibility = View.VISIBLE
                    tvStatus.text = data.status
                }

                itemView.setOnClickListener{
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data: Data)
    }

}