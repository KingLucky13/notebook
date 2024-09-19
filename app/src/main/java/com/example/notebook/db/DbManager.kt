package com.example.notebook.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager(val context: Context) {
    val dbHelper = DbHelper(context)
    var db:SQLiteDatabase? = null

    fun openDb(){
        db = dbHelper.writableDatabase
    }

    fun closeDb(){
        dbHelper.close()
    }

    fun writeDb(title: String, content: String, url:String){
         val values = ContentValues().apply {
             put(MyDbClass.COLUMN_NAME_TITLE,title)
             put(MyDbClass.COLUMN_NAME_CONTENT,content)
             put(MyDbClass.COLUMN_NAME_URL,url)
         }
        db?.insert(MyDbClass.TABLE_NAME, null, values)
    }

    fun readDb() : ArrayList<Note>{
        val data = ArrayList<Note>()

        val cursor = db?.query(MyDbClass.TABLE_NAME, null, null, null, null, null, null)
        while (cursor?.moveToNext()!!) {
            val dataTitle = cursor?.getString(cursor.getColumnIndexOrThrow(MyDbClass.COLUMN_NAME_TITLE))
            val dataContent = cursor?.getString(cursor.getColumnIndexOrThrow(MyDbClass.COLUMN_NAME_CONTENT))
            val dataUri = cursor?.getString(cursor.getColumnIndexOrThrow(MyDbClass.COLUMN_NAME_URL))

            val item = Note()
            item.title = dataTitle.toString()
            item.content = dataContent.toString()
            item.uri = dataUri.toString()

            data.add(item)
        }
        cursor.close()
        
        return data
    }
}