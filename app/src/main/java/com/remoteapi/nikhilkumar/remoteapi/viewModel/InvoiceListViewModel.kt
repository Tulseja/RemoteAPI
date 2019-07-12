package com.remoteapi.nikhilkumar.remoteapi.viewModel

import android.arch.lifecycle.ViewModel

import com.remoteapi.nikhilkumar.remoteapi.repo.Repository
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Invoice


class InvoiceListViewModel(private val repository: Repository) : ViewModel() {

    var invoiceListResult = repository.getInvoiceListFromDB()

    fun getInvoiceForId(id : Int) : Invoice {
        return repository.getInvoiceForID(id)
    }

}
