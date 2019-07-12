package com.remoteapi.nikhilkumar.remoteapi.responsePOJO

import com.google.gson.annotations.SerializedName



data class ProductsList(@SerializedName("success") var status : String ? = null ,
                        @SerializedName("products") var productList :List<Product>? = null)

data class Product(@SerializedName("name") var name : String ? = null ,
                   @SerializedName("image_url") var url : String ? = null ,
                   @SerializedName("price") var price : Double  = 0.0 ,
                   var quantity : Int = 0)

data class Invoice( var productListJson : String ? = null ,
                    var invoiceId : Int  = 0,
                    var prodList : ArrayList<Product> ? = null,
                    var invoiceName : String ? = null)

