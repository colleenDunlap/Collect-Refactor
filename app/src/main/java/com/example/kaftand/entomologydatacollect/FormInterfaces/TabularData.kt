package com.example.kaftand.entomologydatacollect.FormInterfaces

import android.content.Context
import android.widget.TableRow

interface TabularData <T> {
    val metaData : MetaDataInterface
    var dataArray : ArrayList<T>
    val nRows: Int

    fun getColNames() : ArrayList<String>
    fun createRow(iRow : Int, context: Context) : TableRow
    fun buildInfoRow (context: Context, completeResource: Int) : TableRow
    fun buildInfoHeader(context: Context) : TableRow
    fun checkMissingData(iRow : Int, row: TableRow, missingDataError : String) : Boolean
}