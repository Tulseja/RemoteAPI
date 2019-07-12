package com.remoteapi.nikhilkumar.remoteapi.utils

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.remoteapi.nikhilkumar.remoteapi.repo.Repository
import com.remoteapi.nikhilkumar.remoteapi.viewModel.CreateInvoiceViewModel
import com.remoteapi.nikhilkumar.remoteapi.viewModel.InvoiceListViewModel
import com.remoteapi.nikhilkumar.remoteapi.viewModel.InvoiceProdcutListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
        private val application: Application,
        private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(InvoiceProdcutListViewModel::class.java) -> InvoiceProdcutListViewModel(repository)
                    isAssignableFrom(CreateInvoiceViewModel::class.java) -> CreateInvoiceViewModel(repository)
                    isAssignableFrom(InvoiceListViewModel::class.java) -> InvoiceListViewModel(repository)
                    else -> throw IllegalArgumentException("Unknown viewmodel class ${modelClass.name}")
                }
            } as T

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE
                ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE
                            ?: ViewModelFactory(
                                    application, Injection.provideRepository(application)
                            )
                                    .also { INSTANCE = it }
                }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}