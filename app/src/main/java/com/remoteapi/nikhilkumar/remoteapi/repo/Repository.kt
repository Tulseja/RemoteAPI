package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource

interface Repository {
    fun getPlayerList() : LiveData<Resource<List<PlayerData>>>
    fun getMatchList():LiveData<Resource<List<MatchData>>>
}