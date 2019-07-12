package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.*
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource

interface Repository {
    fun getPlayerList() : LiveData<Resource<List<PlayerData>>>
    fun getMatchList():LiveData<Resource<List<MatchData>>>
    fun getInvoiceList() : LiveData<Resource<List<Invoice>>>
    fun getProductList() : LiveData<Resource<List<Product>>>
    fun saveInvoice(list : List<Product>)
    fun getInvoiceListFromDB() : List<Invoice>
    fun getProductListFromDb(id : Int) : List<Product>
}