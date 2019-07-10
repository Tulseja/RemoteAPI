package com.remoteapi.nikhilkumar.remoteapi.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.loadJSONFromRaw
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.utils.Status
import com.remoteapi.nikhilkumar.remoteapi.utils.*
import com.remoteapi.nikhilkumar.remoteapi.viewModel.MyContestListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import obtainViewModel


class MyContestHomeActivity :  MyContestHomeAdapter.ItemClickListener,AppCompatActivity(){
    override fun onItemSelected(id : Int) {
        val filterList   = allMatchList.filter {
            it.player2Data?.id == id || it.player1Data?.id == id
        }
        MatchesListActivity.startActivity(this,filterList as ArrayList<MatchData>,id , map)
    }

    private var isLoading = false
    private var isLastPage = false
    private var allMatchList = listOf<MatchData>()
    lateinit var viewModel: MyContestListViewModel
    var map : HashMap<Int,String> = HashMap()
    var mHomeAdapter : MyContestHomeAdapter ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
        initializeRecyclerView()
    }


    fun initializeRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(this)
        homeRv.layoutManager = linearLayoutManager

        mHomeAdapter = MyContestHomeAdapter(this)
        homeRv.adapter = mHomeAdapter


        val decorator = DividerItemDecoration( this,LinearLayoutManager.VERTICAL)
        homeRv.addItemDecoration(decorator)
    }

    private fun observeViewModel(){
        viewModel = obtainViewModel(MyContestListViewModel::class.java)
        viewModel.loadDataReason("playerlist")

        viewModel.playerListResult.observe(this, Observer {

            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        if (!it.isPaginatedLoading) {
                            homeRv.hide()
                            errorLyt.hide()
                            progress_bar.show()
                        }
                        isLoading = true
                    errorLyt.hide()
                    }
                    Status.ERROR -> {
                        isLoading = false
                        progress_bar.hide()
                        errorLyt.show()
                        homeRv.hide()
                        mHomeAdapter?.removeLoadingViewFooter()
                    }
                    Status.SUCCESS -> {
                        isLoading = false
                        progress_bar.hide()
                        errorLyt.hide()
                        mHomeAdapter?.removeLoadingViewFooter()
                        it.data?.let {
                            homeRv.show()
                            bindView(it)
                        }
                    }
                }
            }

        })
        viewModel.matchListResult.observe(this, Observer {



        })

    }

    fun getMatchList() : List<MatchData> {
        val json = loadJSONFromRaw(this,R.raw.starwarsmatches)
        val gson = Gson()
        val matchListType = object : TypeToken<List<MatchData>>() {}.type
         val list : List<MatchData> = gson.fromJson(json, matchListType)
        Log.d("AK",json)
        allMatchList = list
        return list
    }

    private fun bindView(contestList : List<PlayerData>){
        val matchList = getMatchList()
        for(i in contestList) {
            map[i.id!!] = i.name!!
            val filteredList = matchList.filter {
                it.player1Data?.id == i.id ||
                        it.player2Data?.id == i.id
            }
            var finalScore = 0
            for(matchData in filteredList){
                if(matchData.player2Data?.id == i.id) {
                    if (matchData.player2Data?.score != null && matchData.player1Data?.score != null) {
                        if(matchData.player2Data?.score!! > matchData.player1Data?.score!!) {
                            finalScore += 3
                        } else if(matchData.player2Data?.score!! == matchData.player1Data?.score!!){
                            finalScore += 1
                        }
                    }
                }
                if(matchData.player1Data?.id == i.id) {
                    if (matchData.player1Data?.score != null && matchData.player2Data?.score != null) {
                        if(matchData.player1Data?.score!! > matchData.player2Data?.score!!) {
                            finalScore += 3
                        }else if(matchData.player2Data?.score!! == matchData.player1Data?.score!!){
                            finalScore += 1
                        }

                    }
                }
            }
            i.score = finalScore

        }
        val sortedList = contestList.sortedByDescending { it.score }
        mHomeAdapter?.updateData(sortedList)
    }




}