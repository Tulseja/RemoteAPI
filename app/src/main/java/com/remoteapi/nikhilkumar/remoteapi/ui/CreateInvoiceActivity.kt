package com.remoteapi.nikhilkumar.remoteapi.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.utils.Status
import com.remoteapi.nikhilkumar.remoteapi.utils.*
import com.remoteapi.nikhilkumar.remoteapi.viewModel.CreateInvoiceViewModel
import kotlinx.android.synthetic.main.invoice_details_lyt.*

import obtainViewModel



class CreateInvoiceActivity :  CreateInvoiceAdapter.ItemClickListener,AppCompatActivity(){

    override fun onQuantityIncreased(id: Int) {

    }

    override fun onQuantityDecreased(id: Int) {

    }

    lateinit var viewModel: CreateInvoiceViewModel
    var list = mutableListOf<Product>()
    var mHomeAdapter : CreateInvoiceAdapter ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invoice_details_lyt)
        observeViewModel()
        initializeRecyclerView()
        setListeners()
    }

    private fun setListeners(){

        create_inv_tv.setOnClickListener {
//            val id = getInvoiceId()
            val map = mHomeAdapter?.getAllSelectedProducts()
              val list = map?.keys?.toList()
            if(list?.isNotEmpty() == true) {
                viewModel.createInvoice(list)
                finish()
            }
        }
    }

    fun getInvoiceId() : String{
        return (System.currentTimeMillis()).toString()
    }

    private fun initializeRecyclerView(){
        mHomeAdapter = CreateInvoiceAdapter(this)
        invoiceRv.adapter = mHomeAdapter
    }

    private fun observeViewModel(){
        viewModel = obtainViewModel(CreateInvoiceViewModel::class.java)


        viewModel.prodListLiveData.observe(this, Observer {

            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        if (!it.isPaginatedLoading) {
                            invoiceRv.hide()
                            errorLyt.hide()
                            progress_bar.show()
                        }
                    errorLyt.hide()
                    }
                    Status.ERROR -> {
                        progress_bar.hide()
                        errorLyt.show()
                        invoiceRv.hide()
                    mHomeAdapter?.removeLoadingViewFooter()
                    }
                    Status.SUCCESS -> {
                        progress_bar.hide()
                        errorLyt.hide()
                        mHomeAdapter?.removeLoadingViewFooter()
                        it.data?.let {
                            invoiceRv.show()
                            bindView(it)
                        }
                    }
                }
            }

        })


    }


    private fun bindView(contestList : List<Product>){
        mHomeAdapter?.updateData(contestList)
    }




}