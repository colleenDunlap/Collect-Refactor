package com.example.kaftand.entomologydatacollect.Util

import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.R

object checkMissingDataInLayout {
    fun isDataMissingSetError(table: TableLayout) : Boolean {
        var dataMissing = false
        for (iRow in 0 until table.childCount) {
            val thisRow = table.getChildAt(iRow) as TableRow
            for (iCol in 0 until thisRow.childCount) {
                try {
                    val thisCell = thisRow.getChildAt(iCol) as TextView
                    if ((thisCell.text == null) or (thisCell.text.toString() == "")) {
                        thisCell.setError(table.context.getString(R.string.missing_data))
                        dataMissing = true
                    }
                } catch (e : Exception) {

                }
            }
        }
        return dataMissing
    }

}