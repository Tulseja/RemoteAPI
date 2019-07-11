package com.remoteapi.nikhilkumar.remoteapi.viewModel

import android.arch.lifecycle.ViewModel

import com.remoteapi.nikhilkumar.remoteapi.repo.Repository


class InvoiceListViewModel(private val repository: Repository) : ViewModel() {

    var invoiceListResult = repository.getInvoiceListFromDB()

}
