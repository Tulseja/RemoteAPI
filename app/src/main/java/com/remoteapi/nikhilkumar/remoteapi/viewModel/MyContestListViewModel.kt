package com.remoteapi.nikhilkumar.remoteapi.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.remoteapi.nikhilkumar.remoteapi.loadJSONFromRaw
import com.remoteapi.nikhilkumar.remoteapi.repo.Repository
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.remoteapi.nikhilkumar.remoteapi.utils.Status

class MyContestListViewModel(private val repository: Repository) : ViewModel() {

    private var pageNum = 1
    private var pageSize = 5
    var _reason = MutableLiveData<String>()

    private val playerListLiveData = MutableLiveData<Pair<Int, Int?>>()

    fun loadDataReason(reason  : String){
        _reason.value = reason
    }

    private val myContestListLiveData = MutableLiveData<List<MyContestAPIElement>>()

    var playerListResult = Transformations.switchMap(_reason) {
          repository.getPlayerList()
        /*return@switchMap Transformations.map(result) {
            mapScoresToPlayers(it)
        }*/
    }


    var matchListResult = Transformations.map(_reason){
         repository.getMatchList()
    }


   private fun mapScoresToPlayers(result : Resource<List<PlayerData>>) : Resource<List<PlayerData>>{
       when(result.status){
           Status.SUCCESS ->{
               val matchList = getMatchList()
               for(i in result.data!!) {
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
               Resource.success(result.data)
           }
       }

       return Resource.success(result.data!!)

   }

    fun getMatchList() : List<MatchData>{
        val list = mutableListOf<MatchData>()
        val matchList = matchListResult.value
        if(matchListResult.value == Status.SUCCESS){
            return matchList?.value?.data!!
        }
        return list
    }

    /*fun setPageData(pair: Pair<Int, Int>) {
        myContestParamsLiveData.value = pair
    }

    fun loadNextPage() {
        pageNum++
        val pair = Pair(pageNum,pageSize)
        myContestParamsLiveData.value = pair
    }*/



}
