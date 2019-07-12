package com.remoteapi.nikhilkumar.remoteapi.ui

import android.app.Activity
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


class HomeActivity :  AppCompatActivity()  , HomeInvoicesAdapter.ItemClickListener{
    override fun onItemSelected(id: Int) {
        openInvoiceDetailActivity(id)
    }

    private fun openInvoiceDetailActivity(id : Int){
        InvoiceDetailsActivity.startActivity(this,id)
    }

    lateinit var viewModel: InvoiceListViewModel
    val CREATE_INVOICE_CODE = 123
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
        startActivityForResult(intent,CREATE_INVOICE_CODE )

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
    }


    private fun bindView(invoiceList : List<Invoice>){
        mHomeAdapter?.updateData(invoiceList)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_INVOICE_CODE){
            if(resultCode == Activity.RESULT_OK){
                val id = data?.getLongExtra("id", 0)
                val inv = viewModel.getInvoiceForId(id?.toInt() ?: 0)
                val list = ArrayList<Invoice>()
                list.add(inv)
                mHomeAdapter?.updateData(list)
                noInvoiceLyt.hide()

            }
        }
    }
}