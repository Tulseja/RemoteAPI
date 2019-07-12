package com.remoteapi.nikhilkumar.remoteapi.repo

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log

import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.remoteapi.nikhilkumar.remoteapi.INVOICE_DATA
import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.loadJSONFromRaw
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.*
import com.remoteapi.nikhilkumar.remoteapi.utils.CacheManager
import com.remoteapi.nikhilkumar.remoteapi.utils.INVOICE_TABLE
import com.remoteapi.nikhilkumar.remoteapi.utils.InvoiceDBHelper







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

    fun getProdListFromJson() : LiveData<Resource<ProductsList>>{

        val data = MutableLiveData<Resource<ProductsList>>()
        val json = loadJSONFromRaw(appContext, R.raw.prodlistsample)
        val gson = Gson()
        val listLiveData : ProductsList = gson.fromJson(json,ProductsList::class.java)
        Log.d("AK",json)
        data.value = Resource.success(listLiveData)
        return data
    }



    fun getInvoiceListFromCache() : LiveData<Resource<List<Invoice>>> {
        val data = MutableLiveData<Resource<List<Invoice>>>()
        val cacheManager = CacheManager.getCacheManagerInstance(appContext)
        val json  = cacheManager.retrieveData(INVOICE_DATA)
        val gson = Gson()
        val invoiceListType = object : TypeToken<List<Invoice>>() {}.type
        val listLiveData : List<Invoice> = gson.fromJson(json, invoiceListType)
        Log.d("AK",json)
        data.value = Resource.success(listLiveData)
        return data
    }

    fun getInvoiceListFromDB() : List<Invoice>{
        val list =  InvoiceDBHelper.getInstance(appContext).getInvoiceList(INVOICE_TABLE)
        return list
    }

    fun saveInvoice(json : String){
        CacheManager.getCacheManagerInstance(appContext).saveData(INVOICE_DATA,json)
    }

    fun saveInvoiceListToDB(list : ArrayList<Product>)  {
        InvoiceDBHelper.getInstance(appContext).insertInvoiceList(list, INVOICE_TABLE)
    }

    fun productListFromDB(id : Int) : List<Product>{
        val json = InvoiceDBHelper.getInstance(appContext).getProductListJson(id)
        val gson = Gson()
        val listType = object : TypeToken<List<Product>>() {}.type
        val prodList = gson.fromJson<List<Product>>(json, listType)
        return prodList
    }

}