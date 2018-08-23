package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import kotlin.properties.Delegates
import android.text.Editable
import android.text.TextWatcher
import com.google.gson.Gson


class HLCDataTable(override val metaData: HLCMetaData, override val nRows: Int) : TabularData {
    override var dataArray  = ArrayList<DataEntryInterface>()
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


    override fun createRow(iRow : Int, context: Context) : TableRow {

        var row = TableRow(context)
        var thisRow = this.dataArray.get(iRow) as HLCDataEntry
        var hourView = AssociativeTextView(context, HLCDataEntry::HOUR)
        hourView.setText(thisRow.HOUR)
        row.addView(hourView)

        var gambiaeView = AssociativeEditText(context, HLCDataEntry::GAMBIAE)
        gambiaeView.inputType = InputType.TYPE_CLASS_NUMBER
        gambiaeView.setText(thisRow.GAMBIAE.toString())
        gambiaeView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setGambiae(s.toString().toInt(), iRow)
            }
        })
        row.addView(gambiaeView)

        var culexView = AssociativeEditText(context, HLCDataEntry::CULEX)
        culexView.inputType = InputType.TYPE_CLASS_NUMBER
        culexView.setText(thisRow.CULEX.toString())
        culexView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setCulex(s.toString().toInt(), iRow)
            }
        })
        row.addView(culexView)

        var funestusView = AssociativeEditText(context, HLCDataEntry::FUNESTUS)
        funestusView.inputType = InputType.TYPE_CLASS_NUMBER
        funestusView.setText(thisRow.FUNESTUS.toString())
        funestusView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setFunestus(s.toString().toInt(), iRow)
            }
        })
        row.addView(funestusView)
        var coustaniView = AssociativeEditText(context, HLCDataEntry::COUSTANI)
        coustaniView.inputType = InputType.TYPE_CLASS_NUMBER
        coustaniView.setText(thisRow.COUSTANI.toString())
        coustaniView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setCoustani(s.toString().toInt(), iRow)
            }
        })
        row.addView(coustaniView)

        var mansoniaView = AssociativeEditText(context, HLCDataEntry::MANSONIA)
        mansoniaView.inputType = InputType.TYPE_CLASS_NUMBER
        mansoniaView.setText(thisRow.MANSONIA.toString())
        mansoniaView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setMansonia(s.toString().toInt(), iRow)
            }
        })
        row.addView(mansoniaView)

        var aedesView = AssociativeEditText(context, HLCDataEntry::AEDES)
        aedesView.inputType = InputType.TYPE_CLASS_NUMBER
        aedesView.setText(thisRow.AEDES.toString())
        aedesView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setAedes(s.toString().toInt(), iRow)
            }
        })
        row.addView(aedesView)

        var coquilettidiaView = AssociativeEditText(context, HLCDataEntry::COQUILETTIDIA)
        coquilettidiaView.inputType = InputType.TYPE_CLASS_NUMBER
        coquilettidiaView.setText(thisRow.COQUILETTIDIA.toString())
        coquilettidiaView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setCoquilettidia(s.toString().toInt(), iRow)
            }
        })
        row.addView(coquilettidiaView)

        var otherView = AssociativeEditText(context, HLCDataEntry::OTHER)
        otherView.inputType = InputType.TYPE_CLASS_NUMBER
        otherView.setText(thisRow.OTHER.toString())
        otherView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setOther(s.toString().toInt(), iRow)
            }
        })
        row.addView(otherView)
        return row
    }

    fun setGambiae(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).GAMBIAE = number
    }

    fun setCulex(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).CULEX = number
    }

    fun setFunestus(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).FUNESTUS = number
    }

    fun setCoustani(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).COUSTANI = number
    }

    fun setMansonia(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).MANSONIA = number
    }

    fun setAedes(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).AEDES = number
    }

    fun setCoquilettidia(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).COQUILETTIDIA = number
    }

    fun setOther(number : Int, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).OTHER = number
    }


    override fun checkMissingData(iRow : Int, row: TableRow, missingDataError : String) : Boolean {

        val nElements = row.childCount;
        var castedArray = dataArray as ArrayList<HLCDataEntry>
        var missingData = false
        for(iCol : Int in 0..(nElements-1))
        {
            var thisTableCell = row.getChildAt(iCol)
            var thisTableCellTag = thisTableCell.getTag().toString()
            if (row.getChildAt(iCol) is AssociativeTextView)
            {

            } else if (row.getChildAt(iCol) is AssociativeEditText) {
                thisTableCell = thisTableCell as AssociativeEditText
                //if (castedArray[iRow].get(thisTableCell.propertyRepresented))
            }

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
                    (row.getChildAt(iCol) as EditText).setError(missingDataError)
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

    override fun buildInfoRow(context: Context) : TableRow
    {
        var tr = TableRow(context)
        return tr

    }

    override fun buildInfoHeader(context: Context) : TableRow
    {
        var tr = TableRow(context)
        return tr
    }
}