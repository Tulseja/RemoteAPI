package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MatchData

import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.PlayerData
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.remoteapi.nikhilkumar.remoteapi.utils.Status

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getPlayerList(): LiveData<Resource<List<PlayerData>>> {
        val playerList  = remoteDataSource.getPlayerDataFromJson()
        return Transformations.map(playerList){
            when(it.status){
                Status.SUCCESS ->{
                    if(it.data?.isNotEmpty() == true){
                        Resource.success(it.data!!)
                    } else Resource.error(it.error)
                }
                Status.ERROR ->{
                    Resource.error(it.error)
                }
                Status.LOADING->{
                    Resource.loading()
                }
            }
        }
    }

    override fun getMatchList(): LiveData<Resource<List<MatchData>>> {
        val matchList  = remoteDataSource.getMatchDataFromJson()
        return Transformations.map(matchList){
            when(it.status){
                Status.SUCCESS ->{
                    if(it.data?.isNotEmpty() == true){
                        Resource.success(it.data!!)
                    } else Resource.error(it.error)
                }
                Status.ERROR ->{
                    Resource.error(it.error)
                }
                Status.LOADING->{
                    Resource.loading()
                }
            }
        }
    }



}