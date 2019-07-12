package com.remoteapi.nikhilkumar.remoteapi.ui

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.ProductsList
import com.remoteapi.nikhilkumar.remoteapi.utils.PaginationAdapter
import com.remoteapi.nikhilkumar.remoteapi.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class InvoiceDetailsAdapter : PaginationAdapter<Product>() {


    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prodcut_item_lyt, parent, false)
        return AllProductViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is AllProductViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(Product())
    }

    fun updateData(contestList : List<Product>){
        val currentSize = itemCount
        dataList.addAll(contestList)
        notifyItemRangeInserted(currentSize, contestList.size)
    }


     class AllProductViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {

        val context by lazy { itemView.context }
        val priceTv = itemView.findViewById<TextView>(R.id.price_tv)
        val prodTv= itemView.findViewById<TextView>(R.id.prod_tv)
         val prodIv= itemView.findViewById<ImageView>(R.id.prod_iv)
         val countTv = itemView.findViewById<TextView>(R.id.count_tv)

        fun bind(prod : Product) {
            countTv.text = prod.quantity.toString()
            priceTv.text = prod.price.toString()
            prodIv.loadImage(prod.url)
            prodTv.text = prod.name ?: ""
        }
    }
}