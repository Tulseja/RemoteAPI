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
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.utils.PaginationAdapter
import com.remoteapi.nikhilkumar.remoteapi.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MatchListAdapter : PaginationAdapter<MatchData>() {
    var map = HashMap<Int,String>()
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_list_item, parent, false)
        return AllMatchViewHolder(view,map)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is AllMatchViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(MatchData())
    }

    fun updateData(contestList : List<MatchData> , map : HashMap<Int,String>){
        this.map = map
        val currentSize = itemCount
        dataList.addAll(contestList)
        notifyDataSetChanged(/*currentSize, contestList.size*/)
    }

     class AllMatchViewHolder(itemView: View ,val map : HashMap<Int,String>) : RecyclerView.ViewHolder(itemView) {

        val context by lazy { itemView.context }
        val player1NameTv = itemView.findViewById<TextView>(R.id.player_one_name_tv)
         val player2NameTv = itemView.findViewById<TextView>(R.id.player_two_name_tv)
         val playerScoreTv= itemView.findViewById<TextView>(R.id.players_score_tv)

        fun bind(match : MatchData) {
            player1NameTv.text = map[match.player1Data?.id]
            player2NameTv.text = map[match.player2Data?.id]
            val finalScore = match.player1Data?.score.toString() + "-"+match.player2Data?.score.toString()
            playerScoreTv.text = finalScore
        }

    }

}