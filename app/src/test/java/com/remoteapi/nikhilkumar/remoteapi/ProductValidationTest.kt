package com.remoteapi.nikhilkumar.remoteapi

import android.webkit.URLUtil
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.regex.Pattern


class ProductValidationTest {

    // Product Price
    // Product NAme
    // Product img url
    // quantity
    // invoice ID Numeric
    // order Total Double data type same as product price

    @Before
    fun setUp() {

    }

    @Test
    fun testProductPriceAndOrderTotalValid() {
        val price  = 0
        val priceDouble = price.toDouble()
        Assert.assertNotNull(priceDouble)
    }

    @Test
    fun isProductNameValid() {
        val txt = "#@$%"
        val regx = "[a-zA-Z]+\\.?"
        val pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(txt)
        assert(matcher.find())
    }
    // requires mockito
    @Test
    fun isUrlValid(){
        val url = "sfdasd"
        assert((URLUtil.isValidUrl(url)))
    }

    @Test
    fun isQuantityValid(){
        val quant = 3.5
        assertNotNull(quant.toInt())
    }

    @Test
    fun isInvoiceIdValid(){
        val txt = "#@3yi24$%"
        val regx = "^[0-9]*$"
        val pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(txt)
        assert(matcher.find())

    }

    @After
    fun cleanUp(){

    }


}