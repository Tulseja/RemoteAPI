package com.remoteapi.nikhilkumar.remoteapi.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Invoice
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.utils.PaginationAdapter
import com.remoteapi.nikhilkumar.remoteapi.utils.loadImage

class HomeInvoicesAdapter(val clickListener: ItemClickListener) : PaginationAdapter<Invoice>() {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_invoice_list_item, parent, false)
        return AllInvoiceViewHolder(view,clickListener)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is AllInvoiceViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(Invoice())
    }

    fun updateData(contestList : List<Invoice>){
        val currentSize = itemCount
        dataList.addAll(contestList)
        notifyItemRangeInserted(currentSize, contestList.size)
    }



     class AllInvoiceViewHolder(itemView: View, val clickListener :ItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val context by lazy { itemView.context }
        val invoiceNameTv = itemView.findViewById<TextView>(R.id.invoice_name_tv)
        val invoiceIdTv= itemView.findViewById<TextView>(R.id.invoice_id_tv)

        fun bind(invoice : Invoice) {
            invoiceNameTv.text = context.getString(R.string.invoice_name,invoice.invoiceId.toString())
            invoiceIdTv?.text = invoice.invoiceId.toString()
            itemView.setOnClickListener {
                clickListener.onItemSelected(invoice.invoiceId)
            }
        }
    }

    interface ItemClickListener{
        fun onItemSelected(id : Int)
    }
}