package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.res.ResourcesCompat
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.google.gson.Gson
import java.lang.Math.min
import java.lang.Math.round

class TableEntryView(context : Context,var tableData : TabularData) : TableLayout(context) {


    init {
        this.createTable()
    }

    fun createTable()  {
        var colNames = this.tableData.getColNames()
        for (iRow in 0 until this.tableData.nRows)
        {
            this.addView(this.formatRow(iRow, this.tableData.createRow(iRow, this.context), false))
        }
    }

    fun createHeaderTable() : TableLayout {
        var table = TableLayout(this.context)
        var colNames = this.tableData.getColNames()
        var headerRow = TableRow(this.context)
        for (iCol in 0..(colNames.size-1))
        {
            var thisView = TextView(this.context)
            thisView.setText(colNames[iCol])
            headerRow.addView(thisView)
            this.formatRow(1, headerRow, true)
        }

        table.addView(headerRow)

        return table
    }

    fun formatRow(iRow: Int, row: TableRow, isHeader : Boolean) : TableRow {
        var border : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
        var colWidths = getColWidth()

        if (iRow % 2 == 0) {
            border = ResourcesCompat.getDrawable(resources, R.drawable.border2, null)
        }
        var minTextSize = Float.POSITIVE_INFINITY
        for (iChild in 0 until row.childCount) {
            var cell = row.getChildAt(iChild) as TextView
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cell.background = border
            } else {
                cell.setBackgroundDrawable(border)
            }
            var cellLp = cell.layoutParams
            cellLp.height = round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics));
            var thisWidth = colWidths[iChild]
            cellLp.width = round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, thisWidth, resources.displayMetrics));
            cell.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
            cell.setSelectAllOnFocus(true)
            cell.gravity = Gravity.CENTER
            if (isHeader) {
                minTextSize = min(getTextSize(cell, thisWidth), minTextSize)
            }
        }

        for (iChild in 0 until row.childCount) {
            var cell = row.getChildAt(iChild) as TextView
            if (isHeader) {
                cell.setTextSize(TypedValue.COMPLEX_UNIT_SP, minTextSize)
            }
        }

        return row
    }

    fun getColWidth() : MutableList<Float> {
        var size = this.tableData.getColNames().size
        val displayMetrics = DisplayMetrics()
        val wm : WindowManager = this.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.getDefaultDisplay().getMetrics(displayMetrics)
        val measuredWidth = displayMetrics.widthPixels * 0.9
        var colWidths = mutableListOf<Float>();
        for(iCol in 0 until size)
        {
            colWidths.add(((measuredWidth)/(size.toDouble())).toFloat())
        }
        return colWidths
    }

    fun getTextSize(cell : TextView, cellSize : Float) : Float {
        val originalTextSize = cell.textSize
        val bounds = Rect()
        cell.paint.getTextBounds(cell.text.toString(), 0, cell.text.toString().length, bounds);
        val width = bounds.width().toFloat() * getResources().getDisplayMetrics().scaledDensity
        return (0.8*(cellSize*originalTextSize)/width).toFloat()
    }

    fun collectData(dataArray : Array<Any>) {
        var nRows = this.childCount
        for (iRow in 0 until nRows)
        {
            var thisRow : TableRow = this.getChildAt(iRow) as TableRow
            var nCells = thisRow.childCount
            for (iCell in 0 until nCells)
            {
            }
        }
    }





}

