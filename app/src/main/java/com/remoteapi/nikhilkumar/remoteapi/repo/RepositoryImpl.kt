package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.MyContestAPIElement
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.remoteapi.nikhilkumar.remoteapi.utils.Status

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {


    override fun getMyContestApiResponse(pageNum: Int, pageSize: Int): LiveData<Resource<List<MyContestAPIElement>>> {
        val remoteLiveData = remoteDataSource.getMyContestDetails(pageNum, pageSize)

        return Transformations.map(remoteLiveData) {
            when (it.status) {
                Status.SUCCESS -> {
                    if(it.data != null)
                    Resource.success(it.data!!)
                    else
                    Resource.error(it.error)
                }
                Status.ERROR -> {
                    Resource.error(it.error)
                }
                Status.LOADING -> {
                    Resource.loading(it.isPaginatedLoading)
                }
            }
        }

    }
}