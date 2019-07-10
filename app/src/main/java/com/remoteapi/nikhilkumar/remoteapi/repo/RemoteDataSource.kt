package com.remoteapi.nikhilkumar.remoteapi.repo

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log

import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.loadJSONFromRaw
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData


class RemoteDataSource(private val appContext: Context){

    companion object {
        var DEFAULT_PAGE_NUM =1
        fun getInstance(appContext: Application): RemoteDataSource {
            return RemoteDataSource(appContext)
        }
    }




    /*private fun getHeaderForAPI(): Map<String, String?> {
        val header = HashMap<String, String?>()
        header[APIConstants.AUTHORIZATION] = APIConstants.AUTH_VALUE
        return header
    }*/

    fun getPlayerDataFromJson() : LiveData<Resource<List<PlayerData>>>{
        val data = MutableLiveData<Resource<List<PlayerData>>>()
        data.value = Resource.loading()

        val json = loadJSONFromRaw(appContext, R.raw.starwarsplayers)

        val gson = Gson()
        val playerListType = object : TypeToken<List<PlayerData>>() {}.type
        val listLiveData : List<PlayerData> = gson.fromJson(json, playerListType)
        Log.d("AK",json)
        data.value = Resource.success(listLiveData)

        return data
    }

    fun getMatchDataFromJson() : LiveData<Resource<List<MatchData>>>{
        val data = MutableLiveData<Resource<List<MatchData>>>()
//        data.value = Resource.loading()

        val json = loadJSONFromRaw(appContext, R.raw.starwarsmatches)

        val gson = Gson()
        val matchListType = object : TypeToken<List<MatchData>>() {}.type
        val listLiveData : List<MatchData> = gson.fromJson(json, matchListType)
        Log.d("AK",json)
        data.value = Resource.success(listLiveData)

        return data
    }

}