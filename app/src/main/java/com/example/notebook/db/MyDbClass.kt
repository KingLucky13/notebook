package com.example.notebook.db

import android.provider.BaseColumns

object MyDbClass {
    const val TABLE_NAME = "my_tabLe";
    const val COLUMN_NAME_TITLE = "title";
    const val COLUMN_NAME_CONTENT = "content";
    const val COLUMN_NAME_URL = "url";

    const val DB_NAME = "notes";
    const val DB_VERSION = 2;

    const val  SQL_CREATE_TABLE="CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY,${COLUMN_NAME_TITLE} TEXT,${COLUMN_NAME_CONTENT} TEXT,${COLUMN_NAME_URL} TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
}