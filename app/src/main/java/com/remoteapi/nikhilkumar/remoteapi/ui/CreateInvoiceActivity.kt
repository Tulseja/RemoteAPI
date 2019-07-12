package com.remoteapi.nikhilkumar.remoteapi.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.widget.Toast


import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.utils.Status
import com.remoteapi.nikhilkumar.remoteapi.utils.*
import com.remoteapi.nikhilkumar.remoteapi.viewModel.CreateInvoiceViewModel
import kotlinx.android.synthetic.main.create_invoice_lyt.*

import obtainViewModel



class CreateInvoiceActivity :  AppCompatActivity(){

    lateinit var viewModel: CreateInvoiceViewModel
    var list = mutableListOf<Product>()
    var mHomeAdapter : CreateInvoiceAdapter ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_invoice_lyt)
        observeViewModel()
        initializeRecyclerView()
        setListeners()
    }

    private fun setListeners(){
        create_inv_tv.setOnClickListener {
            val map = mHomeAdapter?.getAllSelectedProducts()
              val list = ArrayList<Product>(map?.values)
                if(list.isNotEmpty()) {
                    val insertedId = viewModel.createInvoice(list)
                    val intent = Intent()
                    intent.putExtra("id",insertedId)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                } else
                    Snackbar.make(create_inv_parent_lyt,getString(R.string.pls_select) , Toast.LENGTH_SHORT).show()
        }

        search_view.setOnQueryTextListener( object  : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!TextUtils.isEmpty(p0))
                    mHomeAdapter?.filter(p0!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(!TextUtils.isEmpty(p0))
                    mHomeAdapter?.filter(p0!!)
                return true
            }

        }

        )
    }

    private fun initializeRecyclerView(){
        mHomeAdapter = CreateInvoiceAdapter()
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