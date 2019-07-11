package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.*
import com.remoteapi.nikhilkumar.remoteapi.utils.CacheManager

import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.remoteapi.nikhilkumar.remoteapi.utils.Status

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getInvoiceListFromDB(): List<Invoice> {
        val list = remoteDataSource.getInvoiceListFromDB()
        return list
    }

    override fun saveInvoice(list: List<Product>) {
        remoteDataSource.saveInvoiceListToDB(list as ArrayList<Product>)
    }

    override fun getInvoiceList(): LiveData<Resource<List<Invoice>>> {
        return remoteDataSource.getInvoiceListFromCache()
    }

    override fun getProductList(): LiveData<Resource<List<Product>>> {
        val prodList  = remoteDataSource.getProdListFromJson()
        return Transformations.map(prodList){
            when(it.status){
                Status.SUCCESS->{
                    if(it.data?.productList?.isNotEmpty() == true){
                        Resource.success(it.data?.productList!!)
                    } else  {
                    Resource.error(it.error)
                    }

                }
                Status.LOADING ->{
                    Resource.loading()
                }
                Status.ERROR -> {
                    Resource.error(it.error)
                }
            }
        }

    }

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