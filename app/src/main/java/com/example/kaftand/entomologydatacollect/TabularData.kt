package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.view.View
import android.widget.TableRow

interface TabularData {
    val metaData : MetaDataInterface
    var dataArray : ArrayList<Any>
    var context : Context
    val nRows: Int

    fun getColNames() : ArrayList<String>
    fun createRow(iRow : Int, context: Context) : TableRow
    fun data2Json() : String
    fun updateRowOfData(iRow : Int, row: TableRow, missingDataError : String) : Boolean
}