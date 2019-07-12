package com.remoteapi.nikhilkumar.remoteapi.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import com.remoteapi.nikhilkumar.remoteapi.R
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Invoice
import com.remoteapi.nikhilkumar.remoteapi.utils.Status
import com.remoteapi.nikhilkumar.remoteapi.utils.*
import com.remoteapi.nikhilkumar.remoteapi.viewModel.InvoiceListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import obtainViewModel


class MyContestHomeActivity :  AppCompatActivity()  , HomeInvoicesAdapter.ItemClickListener{
    override fun onItemSelected(id: Int) {
        openInvoiceDetailActivity(id)
    }

    private fun openInvoiceDetailActivity(id : Int){
        InvoiceDetailsActivity.startActivity(this,id)
    }

    lateinit var viewModel: InvoiceListViewModel

    var mHomeAdapter : HomeInvoicesAdapter ? = null
    val list = mutableListOf<Invoice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecyclerView()
        setListeners()
        observeViewModel()
    }

    private fun setListeners(){
        fab_bottom_btn.setOnClickListener {
            openCreateInvoiceActivity()
        }
    }


    private fun openCreateInvoiceActivity(){
        val intent = Intent(this,CreateInvoiceActivity::class.java)
        startActivity(intent)

    }


    fun initializeRecyclerView(){
        homeRv.layoutManager = LinearLayoutManager(this)
        mHomeAdapter = HomeInvoicesAdapter(this)
        homeRv.adapter = mHomeAdapter
    }

    private fun observeViewModel(){
        viewModel = obtainViewModel(InvoiceListViewModel::class.java)
        list.addAll(viewModel.invoiceListResult)
        if(list.isNotEmpty()) {
            homeRv.show()
            errorLyt.hide()
            progress_bar.hide()
            bindView(list)
        }
        else {
            homeRv.show()
            errorLyt.hide()
            progress_bar.hide()
            noInvoiceLyt.show()
        }

        /*viewModel.invoiceListResult.observe(this, Observer {

            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        if (!it.isPaginatedLoading) {
                            homeRv.hide()
                            errorLyt.hide()
                            progress_bar.show()
                        }

                    errorLyt.hide()
                    }
                    Status.ERROR -> {
                        progress_bar.hide()
                        errorLyt.show()
                        homeRv.hide()
                    mHomeAdapter?.removeLoadingViewFooter()
                    }
                    Status.SUCCESS -> {
                        progress_bar.hide()
                        errorLyt.hide()
                        mHomeAdapter?.removeLoadingViewFooter()
                        it.data?.let {
                            if(it.isNotEmpty()) {
                                homeRv.show()
                                bindView(it)
                            } else {
                                homeRv.hide()
                                noInvoiceLyt.show()
                            }
                        }
                    }
                }
            }

        })*/
    }


    private fun bindView(invoiceList : List<Invoice>){
        mHomeAdapter?.updateData(invoiceList)
    }
}