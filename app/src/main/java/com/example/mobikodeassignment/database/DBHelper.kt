package com.example.mobikodeassignment.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.mobikodeassignment.model.Book

class DBHelper(context: Context) : SQLiteOpenHelper(context,"bookDb", null,1){

    private val keyBookTable = "BOOK"
    private val keyBookId = "bookId"
    private val keyPublicationDate = "publicationDate"
    private val keyArticleType = "articleType"
    private val keyAbstract = "abstract"

    override fun onCreate(db: SQLiteDatabase) {
        val createBookTable = "CREATE TABLE $keyBookTable ($keyBookId text primary key, $keyPublicationDate text, $keyArticleType text, $keyAbstract text)"
        db.execSQL(createBookTable)
        Log.e("db==", "Table created..")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun upsertBookData(bookList:List<Book>){
        if (!bookList.isNullOrEmpty() && bookList.isNotEmpty()){
            val db = this.writableDatabase
            for (i in bookList.indices){
                val book = bookList[i]
                val contentValues = contentValuesOf(
                    keyBookId to book.id,
                    keyPublicationDate to book.publicationDate,
                    keyArticleType to book.articleType,
                    keyAbstract to book.abstract[0]
                )

                try {
                    db.insertWithOnConflict(keyBookTable, null, contentValues,
                        SQLiteDatabase.CONFLICT_NONE
                    )
                } catch (e: Exception) {
                    db.update(keyBookTable, contentValues, "$keyBookId = '${book.id}'", null)
                }
            }
        }
    }

    fun getBookData(): MutableList<Book> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $keyBookTable", null)
        val bookList = mutableListOf<Book>()
        if (cursor != null && cursor.count>0){
            while (cursor.moveToNext()){
                val book = Book(
                    cursor.getString(cursor.getColumnIndex(keyBookId)),
                    cursor.getString(cursor.getColumnIndex(keyPublicationDate)),
                    cursor.getString(cursor.getColumnIndex(keyArticleType)),
                    listOf(cursor.getString(cursor.getColumnIndex(keyAbstract)))
                )
                bookList.add(book)
            }
            try {
                cursor.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return bookList
    }
}
