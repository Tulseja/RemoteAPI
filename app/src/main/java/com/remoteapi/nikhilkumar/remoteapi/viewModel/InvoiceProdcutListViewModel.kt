package com.remoteapi.nikhilkumar.remoteapi.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

import com.remoteapi.nikhilkumar.remoteapi.repo.Repository

import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import org.json.JSONArray
import org.json.JSONObject

class InvoiceProdcutListViewModel(private val repository: Repository) : ViewModel() {

    fun getProdcutListForInvoice(id : Int) : List<Product>{
        val list = repository.getProductListFromDb(id)
        return list
    }
}
