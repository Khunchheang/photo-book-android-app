package com.khunchheang.photobookmark.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.format.Formatter
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object Common {

    fun getVersionName(context: Context): String? {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

}