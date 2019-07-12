package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.*
import com.remoteapi.nikhilkumar.remoteapi.utils.Resource

interface Repository {
    fun getProductList() : LiveData<Resource<List<Product>>>
    fun saveInvoice(list : ArrayList<Product>) : Long
    fun getInvoiceListFromDB() : List<Invoice>
    fun getProductListFromDb(id : Int) : List<Product>
    fun getInvoiceForID(id : Int) : Invoice
}