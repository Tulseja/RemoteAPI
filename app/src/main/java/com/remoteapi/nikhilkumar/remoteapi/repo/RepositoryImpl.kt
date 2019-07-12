package com.remoteapi.nikhilkumar.remoteapi.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.*

import com.remoteapi.nikhilkumar.remoteapi.utils.Resource
import com.remoteapi.nikhilkumar.remoteapi.utils.Status

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getInvoiceForID(id: Int): Invoice {
        return remoteDataSource.getInvoiceForId(id)
    }

    override fun getProductListFromDb(id : Int): List<Product> {
        val list = remoteDataSource.productListFromDB(id)
        return list
    }

    override fun getInvoiceListFromDB(): List<Invoice> {
        val list = remoteDataSource.getInvoiceListFromDB()
        return list
    }

    override fun saveInvoice(list: ArrayList<Product>) : Long {
        return remoteDataSource.saveInvoiceListToDB(list)
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

}