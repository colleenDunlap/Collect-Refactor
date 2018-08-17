package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.R
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class HLCDataTable(override val metaData: HLCMetaData, override val nRows: Int, override var context: Context) : TabularData {
    override var dataArray  = ArrayList<Any>()
    var hourArray: Array<String> by Delegates.notNull()
    init {
        hourArray = arrayOf("18-19:00","19-20:00","20-21:00","21-22:00","22-23:00",
        "23-24:00", "24-01:00", "01-02:00", "02-03:00", "03-04:00", "04-05:00", "05-06:00", "total")
        for (iRow in 0 until this.nRows) {
            var thisEntry = HLCDataEntry(metaData);
            thisEntry.HOUR = hourArray[iRow]
            dataArray.add(thisEntry)
        }
    }

    override fun getColNames(): ArrayList<String> {
        var colnames = ArrayList<String>()
        colnames.add("Hour")
        colnames.add("Gambiae")
        colnames.add("Culex")
        colnames.add("Funestus")
        colnames.add("Coustani")
        colnames.add("Mansonia")
        colnames.add("Aedes")
        colnames.add("Coquilettidia")
        colnames.add("Other")
        return colnames
    }

    override fun data2Json() : String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRow(iRow : Int, context: Context) : TableRow {
        var row = TableRow(context)
        var thisRow = this.dataArray.get(iRow) as HLCDataEntry
        var hourView = AssociativeTextView(context, HLCDataEntry::HOUR)
        hourView.setText(thisRow.HOUR)
        hourView.tag = "HOUR"
        row.addView(hourView)
        var gambiaeView = EditText(context)
        gambiaeView.inputType = InputType.TYPE_CLASS_NUMBER
        gambiaeView.setText(thisRow.GAMBIAE.toString())
        gambiaeView.tag = "GAMBIAE"
        row.addView(gambiaeView)
        var culexView = EditText(context)
        culexView.inputType = InputType.TYPE_CLASS_NUMBER
        culexView.setText(thisRow.CULEX.toString())
        culexView.tag = "CULEX"
        row.addView(culexView)
        var funestusView = EditText(context)
        funestusView.inputType = InputType.TYPE_CLASS_NUMBER
        funestusView.setText(thisRow.FUNESTUS.toString())
        funestusView.tag = "FUNESTUS"
        row.addView(funestusView)
        var coustaniView = EditText(context)
        coustaniView.inputType = InputType.TYPE_CLASS_NUMBER
        coustaniView.setText(thisRow.COUSTANI.toString())
        coustaniView.tag = "COUSTANI"
        row.addView(coustaniView)
        var mansoniaView = EditText(context)
        mansoniaView.inputType = InputType.TYPE_CLASS_NUMBER
        mansoniaView.setText(thisRow.MANSONIA.toString())
        mansoniaView.tag = "MANSONIA"
        row.addView(mansoniaView)
        var aedesView = EditText(context)
        aedesView.inputType = InputType.TYPE_CLASS_NUMBER
        aedesView.setText(thisRow.AEDES.toString())
        aedesView.tag = "AEDES"
        row.addView(aedesView)
        var coquilettidiaView = EditText(context)
        coquilettidiaView.inputType = InputType.TYPE_CLASS_NUMBER
        coquilettidiaView.setText(thisRow.COQUILETTIDIA.toString())
        coquilettidiaView.tag = "COQUILETTIDIA"
        row.addView(coquilettidiaView)
        var otherView = EditText(context)
        otherView.inputType = InputType.TYPE_CLASS_NUMBER
        otherView.setText(thisRow.OTHER.toString())
        otherView.tag = "OTHER"
        row.addView(otherView)
        return row
    }

    fun getPropertyRowMap() : Array<KProperty<*>> {
        return arrayOf(HLCDataEntry::HOUR, HLCDataEntry::GAMBIAE, HLCDataEntry::CULEX, HLCDataEntry::FUNESTUS)
    }

    override fun updateRowOfData(iRow : Int, row: TableRow, missingDataError : String) : Boolean {

        val nElements = row.childCount;
        var castedArray = dataArray as ArrayList<HLCDataEntry>
        var missingData = false
        for(iCol : Int in 0..(nElements-1))
        {
            var thisTableCell = row.getChildAt(iCol)
            var thisTableCellTag = thisTableCell.getTag().toString()
            if (thisTableCellTag == "HOUR") {
                castedArray[iRow].HOUR = (row.getChildAt(iCol) as TextView).getText().toString()
            } else if (thisTableCellTag == "GAMBIAE") {
                castedArray[iRow].GAMBIAE = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].GAMBIAE == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
                    missingData = true
                }
            } else if (thisTableCellTag == "FUNESTUS") {
                castedArray[iRow].FUNESTUS = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].FUNESTUS == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(this.context.getString(R.string.missing_data))
                    missingData = true
                }
            } else if (thisTableCellTag == "COUSTANI") {
                castedArray[iRow].COUSTANI = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].COUSTANI == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
                    missingData = true
                }
            } else if (thisTableCellTag == "MANSONIA") {
                castedArray[iRow].MANSONIA = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].MANSONIA == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
                    missingData = true
                }
            } else if (thisTableCellTag == "AEDES") {
                castedArray[iRow].AEDES = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].AEDES == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
                    missingData = true
                }
            } else if (thisTableCellTag == "COQUILETTIDIA") {
                castedArray[iRow].COQUILETTIDIA = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].COQUILETTIDIA == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
                    missingData = true
                }
            } else if (thisTableCellTag == "OTHER") {
                castedArray[iRow].OTHER = (row.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                if (castedArray[iRow].OTHER == null)
                {
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
                    missingData = true
                }
            } else {
                throw error("Incorrect Tag supplied")
            }
        }
        return missingData
    }
}