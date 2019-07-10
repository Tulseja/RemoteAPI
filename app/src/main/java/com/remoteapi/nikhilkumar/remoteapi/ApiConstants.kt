package com.remoteapi.nikhilkumar.remoteapi

import android.content.Context
import android.support.annotation.RawRes
import java.io.IOException


fun loadJSONFromRaw(context: Context, @RawRes file: Int): String {
    var json: String = ""
    try {
        val input = context.resources.openRawResource(file)
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        json = String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return json
}

