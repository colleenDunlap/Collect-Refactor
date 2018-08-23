package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.view.View
import android.widget.TableRow

interface TabularData {
    val metaData : MetaDataInterface
    var dataArray : ArrayList<DataEntryInterface>
    val nRows: Int

    fun getColNames() : ArrayList<String>
    fun createRow(iRow : Int, context: Context) : TableRow
    fun buildInfoRow (context: Context) : TableRow
    fun buildInfoHeader(context: Context) : TableRow
    fun checkMissingData(iRow : Int, row: TableRow, missingDataError : String) : Boolean
}