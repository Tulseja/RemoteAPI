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
import com.remoteapi.nikhilkumar.remoteapi.utils.PaginationAdapter
import com.remoteapi.nikhilkumar.remoteapi.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*

class MyContestHomeAdapter(val clickListener: ItemClickListener) : PaginationAdapter<PlayerData>() {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_contest_list_tem, parent, false)
        return AllPlayerViewHolder(view,clickListener)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is AllPlayerViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(PlayerData())
    }

    fun updateData(contestList : List<PlayerData>){
        val currentSize = itemCount
        dataList.addAll(contestList)
        notifyItemRangeInserted(currentSize, contestList.size)
    }

     class AllPlayerViewHolder(itemView: View , val clickListener :ItemClickListener ) : RecyclerView.ViewHolder(itemView) {

        val context by lazy { itemView.context }
        val playerNameTv = itemView.findViewById<TextView>(R.id.player_name_tv)
        val playerIconIv  = itemView.findViewById<ImageView>(R.id.player_iv)
         val playerScoreTv= itemView.findViewById<TextView>(R.id.player_score_tv)

        fun bind(player : PlayerData) {
            playerNameTv.text = player.name ?: ""
            playerIconIv?.loadImage(player.icon)
            playerScoreTv.text = player.score?.toString() ?: ""

            itemView.setOnClickListener {
                clickListener.onItemSelected(player.id!!)
            }
        }



    }

    interface ItemClickListener{
        fun onItemSelected(id : Int)
    }
}