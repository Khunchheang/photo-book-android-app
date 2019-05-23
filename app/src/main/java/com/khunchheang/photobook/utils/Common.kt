package com.khunchheang.photobook.utils

import android.content.Context

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