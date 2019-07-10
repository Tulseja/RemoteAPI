package com.remoteapi.nikhilkumar.remoteapi.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration

import android.support.v7.widget.LinearLayoutManager
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.utils.Status
import com.remoteapi.nikhilkumar.remoteapi.utils.*
import com.remoteapi.nikhilkumar.remoteapi.viewModel.MyContestListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.match_list_activity_layout.*
import obtainViewModel


class MatchesListActivity : AppCompatActivity(){

    private var isLoading = false
    private var isLastPage = false
    lateinit var viewModel: MyContestListViewModel
    var mHomeAdapter : MatchListAdapter ? = null
    var id : Int  = 0
    var map : HashMap<Int, String> ? = null

    var matchList = ArrayList<MatchData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_list_activity_layout)
        if(intent?.hasExtra("match") == true){
            matchList = intent?.getSerializableExtra("match") as ArrayList<MatchData>
        }
        map = intent.getSerializableExtra("map") as HashMap<Int, String>
        id = intent.getIntExtra("id" , 0)

//        observeViewModel()
        initializeRecyclerView()
        loadData()

    }

    fun loadData(){
        mHomeAdapter?.updateData(matchList,map!!)
    }

    companion object {
        fun startActivity(activity : Activity, list : ArrayList<MatchData> , id : Int , map : HashMap<Int, String>){
            val intent = Intent(activity,MatchesListActivity::class.java)
            intent.putExtra("match",list)
            intent.putExtra("id" ,id)
            intent.putExtra("map" , map)
            activity.startActivity(intent)
        }
    }

    fun initializeRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(this)
        matches_rv.layoutManager = linearLayoutManager

        mHomeAdapter = MatchListAdapter()
        matches_rv.adapter = mHomeAdapter
    }

}