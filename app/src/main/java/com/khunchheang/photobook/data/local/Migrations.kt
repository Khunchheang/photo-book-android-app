package com.khunchheang.photobook.data.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

object Migrations {

    class Migration1TO2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //database.execSQL("UPDATE tb_document SET allow_select = 0 WHERE countryCode = 'ASS'")
        }
    }

    class Migration2TO3 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {

        }
    }

}