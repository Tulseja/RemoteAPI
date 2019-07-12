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
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.*
import com.remoteapi.nikhilkumar.remoteapi.utils.INVOICE_TABLE
import com.remoteapi.nikhilkumar.remoteapi.utils.InvoiceDBHelper

class RemoteDataSource(private val appContext: Context){

    companion object {
        fun getInstance(appContext: Application): RemoteDataSource {
            return RemoteDataSource(appContext)
        }
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

    fun getInvoiceListFromDB() : List<Invoice>{
        val list =  InvoiceDBHelper.getInstance(appContext).getInvoiceList(INVOICE_TABLE)
        return list
    }

    fun saveInvoiceListToDB(list : ArrayList<Product>) : Long  {
        return InvoiceDBHelper.getInstance(appContext).insertInvoiceList(list, INVOICE_TABLE)
    }

    fun productListFromDB(id : Int) : List<Product> {
        val json = InvoiceDBHelper.getInstance(appContext).getProductListJson(id)
        val gson = Gson()
        val listType = object : TypeToken<List<Product>>() {}.type
        val prodList = gson.fromJson<List<Product>>(json, listType)
        return prodList
    }

    fun getInvoiceForId(id : Int) : Invoice{
        return InvoiceDBHelper.getInstance(appContext).getInvoice(id)
    }
}