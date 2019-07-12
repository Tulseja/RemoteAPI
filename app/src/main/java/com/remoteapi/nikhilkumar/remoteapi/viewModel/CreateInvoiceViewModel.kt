package com.remoteapi.nikhilkumar.remoteapi.viewModel

import android.arch.lifecycle.ViewModel

import com.remoteapi.nikhilkumar.remoteapi.repo.Repository

import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import org.json.JSONArray
import org.json.JSONObject

class CreateInvoiceViewModel(private val repository: Repository) : ViewModel() {
    var prodListLiveData = repository.getProductList()


    fun createInvoice(list : ArrayList<Product>) : Long{
        /*val productsList = ArrayList<Product>(map.keys)
        val obj = JSONObject()
        obj.put("id", uniqueId)
        val jsonArr = JSONArray(productsList)
        obj.put("products",jsonArr)*/
        return repository.saveInvoice(list)
    }
}
