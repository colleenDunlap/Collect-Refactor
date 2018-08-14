package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class TableEntryView(context : Context,var tableData : TabularData) : TableLayout(context) {

    init {
        this.createTable()
    }

    fun createTable()  {
        var colNames = this.tableData.getColNames()
        this.addView(this.createHeaderRow())
        for (iRow in 0 until this.tableData.nRows)
        {
            this.addView(this.tableData.createRow(iRow, this.context))
        }
    }

    fun createHeaderRow() : TableRow {
        var colNames = this.tableData.getColNames()
        var headerRow = TableRow(this.context)
        for (iCol in 0..(colNames.size-1))
        {
            var thisView = TextView(this.context)
            thisView.setText(colNames[iCol])
            headerRow.addView(thisView)
        }
        return headerRow
    }


}

