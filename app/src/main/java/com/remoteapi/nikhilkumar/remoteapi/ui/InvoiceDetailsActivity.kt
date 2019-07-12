package com.remoteapi.nikhilkumar.remoteapi.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product
import com.remoteapi.nikhilkumar.remoteapi.utils.Status
import com.remoteapi.nikhilkumar.remoteapi.utils.*
import com.remoteapi.nikhilkumar.remoteapi.viewModel.CreateInvoiceViewModel
import com.remoteapi.nikhilkumar.remoteapi.viewModel.InvoiceProdcutListViewModel
import kotlinx.android.synthetic.main.invoice_detail_lyt.*

import obtainViewModel



class InvoiceDetailsActivity :  AppCompatActivity(){

    lateinit var viewModel: InvoiceProdcutListViewModel
     var invoiceId : Int = -1
    var list = mutableListOf<Product>()
    var mHomeAdapter : InvoiceDetailsAdapter ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invoiceId = intent.getIntExtra("invoiceid", -1)
        setContentView(R.layout.invoice_detail_lyt)
        initializeRecyclerView()
        observeViewModel()

    }

    companion object {
        fun startActivity(activity: Activity, id : Int){
            val intent = Intent(activity,InvoiceDetailsActivity::class.java)
            intent.putExtra("invoiceid" , id)
            activity.startActivity(intent)
        }
    }

    private fun initializeRecyclerView(){
        invoiceDetailRv.layoutManager = LinearLayoutManager(this)
        mHomeAdapter = InvoiceDetailsAdapter()
        invoiceDetailRv.adapter = mHomeAdapter
    }

    private fun observeViewModel(){
        viewModel = obtainViewModel(InvoiceProdcutListViewModel::class.java)
        val list = viewModel.getProdcutListForInvoice(invoiceId)
        if(list.isNotEmpty()){
            progress_bar.hide()
            errorLyt.hide()
            invoiceDetailRv.show()
            bindView(list)
        } else {
            progress_bar.hide()
            errorLyt.show()
            invoiceDetailRv.hide()
        }

        /*viewModel.prodListLiveData.observe(this, Observer {

            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        if (!it.isPaginatedLoading) {
                            invoiceDetailRv.hide()
                            errorLyt.hide()
                            progress_bar.show()
                        }
                    errorLyt.hide()
                    }
                    Status.ERROR -> {
                        progress_bar.hide()
                        errorLyt.show()
                        invoiceDetailRv.hide()
                    mHomeAdapter?.removeLoadingViewFooter()
                    }
                    Status.SUCCESS -> {
                        progress_bar.hide()
                        errorLyt.hide()
                        mHomeAdapter?.removeLoadingViewFooter()
                        it.data?.let {
                            invoiceDetailRv.show()
                            bindView(it)
                        }
                    }
                }
            }

        })
*/

    }


    private fun bindView(prodList : List<Product>){
        mHomeAdapter?.updateData(prodList)
    }




}