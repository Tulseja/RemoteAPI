package com.remoteapi.nikhilkumar.remoteapi.ui
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.utils.PaginationAdapter
import com.remoteapi.nikhilkumar.remoteapi.utils.loadImage
import kotlin.collections.HashMap





class CreateInvoiceAdapter() : PaginationAdapter<Product>() {

    var map = HashMap<String,Product>()
    val itemsCopy  = mutableListOf<Product>()


    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.invoice_item_lyt, parent, false)
        return AllProductViewHolder(view,map)
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
        itemsCopy.addAll(contestList)
        notifyItemRangeInserted(currentSize, contestList.size)
    }

    fun getAllSelectedProducts() : Map<String,Product>{
        return map
    }

    fun filter(text: String) {
        dataList.clear()
        if (text.isEmpty()) {
            dataList.addAll(itemsCopy)
        } else {
            for (item in itemsCopy) {
                if (item.name?.toLowerCase()?.contains(text.toLowerCase()) == true) {
                    dataList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

     class AllProductViewHolder(itemView: View ,val map: HashMap<String,Product>) : RecyclerView.ViewHolder(itemView) {

        val context by lazy { itemView.context }
        val priceTv = itemView.findViewById<TextView>(R.id.price_tv)
        val prodTv= itemView.findViewById<TextView>(R.id.prod_tv)
         val prodIv= itemView.findViewById<ImageView>(R.id.prod_iv)
         val minusIv = itemView.findViewById<ImageView>(R.id.minus_iv)
         val plusIv = itemView.findViewById<ImageView>(R.id.plus_iv)
         val countTv = itemView.findViewById<TextView>(R.id.count_tv)

        fun bind(prod : Product) {
            countTv.text = prod.quantity.toString()
            priceTv.text = prod.price.toString()
            prodIv.loadImage(prod.url)
            prodTv.text = prod.name ?: ""
            minusIv.setOnClickListener {
                if(prod.quantity > 0) {
                    prod.quantity--
                }
                countTv.text = prod.quantity.toString()
                if(!TextUtils.isEmpty(prod.name) && prod.quantity > 0)
                    map[prod.name!!] = prod
                if(prod.quantity == 0){
                    map.remove(prod.name)
                }
            }
            plusIv.setOnClickListener {
                prod.quantity++
                countTv.text = prod.quantity.toString()
                if(!TextUtils.isEmpty(prod.name) && prod.quantity > 0)
                    map[prod.name!!] = prod
                if(prod.quantity == 0){
                    map.remove(prod.name)
                }
            }
        }
    }
}