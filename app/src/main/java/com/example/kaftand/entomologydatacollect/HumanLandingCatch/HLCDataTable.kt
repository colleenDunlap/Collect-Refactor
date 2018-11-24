package com.example.kaftand.entomologydatacollect.HumanLandingCatch

import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import android.widget.ImageView
import com.example.kaftand.entomologydatacollect.*
import com.example.kaftand.entomologydatacollect.FormInterfaces.TabularData
import org.w3c.dom.Text
import java.lang.Math.floor
import java.lang.Math.round


class HLCDataTable(override var metaData: HLCMetaData, override val nRows: Int) : TabularData <HLCDataEntry> {
    override var dataArray  = ArrayList<HLCDataEntry>()
    init {
        val hourArray = arrayOf("18-19:00","19-20:00","20-21:00","21-22:00","22-23:00",
        "23-24:00", "24-01:00", "01-02:00", "02-03:00", "03-04:00", "04-05:00", "05-06:00", "total")
        val inOutArray = arrayOf("in","out")
        for (iRow in 0 until this.nRows) {
            val inOrOut = if(iRow < hourArray.size) {0} else {1}
            var thisEntry = HLCDataEntry(metaData, inOutArray[inOrOut]);
            thisEntry.HOUR = hourArray[iRow%hourArray.size]

            dataArray.add(thisEntry)
        }
    }

    override fun getColNames(context: Context): ArrayList<String> {
        var colnames = ArrayList<String>()
        colnames.add("Form Row")
        colnames.add("Hour")
        colnames.add(context.getString(R.string.indoor) + "/" + context.getString(R.string.outdoor))
        colnames.add("Gambiae")
        colnames.add("Funestus")
        colnames.add("Culex")
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

        var formRowNumber = if(iRow < 13) {iRow + 1} else {iRow}
        var formRow = TextView(context)
        if ((iRow != 12) and (iRow != 25)) {
            formRow.setText(formRowNumber.toString())
        } else {
            formRow.setText("-")
        }

        row.addView(formRow)

        var hourView = TextView(context)
        hourView.setText(thisRow.HOUR)
        row.addView(hourView)

        var inOutView = TextView(context)
        inOutView.setText(if(thisRow.IN_OR_OUT == "in") {context.getString(R.string.indoor)} else {context.getString(R.string.outdoor)})
        row.addView(inOutView)

        var gambiaeView = EditText(context)
        gambiaeView.inputType = InputType.TYPE_CLASS_NUMBER
        gambiaeView.setText(thisRow.GAMBIAE.toString())
        gambiaeView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setGambiae(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(gambiaeView)

        var funestusView = EditText(context)
        funestusView.inputType = InputType.TYPE_CLASS_NUMBER
        funestusView.setText(thisRow.FUNESTUS.toString())
        funestusView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setFunestus(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(funestusView)

        var culexView = EditText(context)
        culexView.inputType = InputType.TYPE_CLASS_NUMBER
        culexView.setText(thisRow.CULEX.toString())
        culexView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setCulex(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(culexView)

        var coustaniView = EditText(context)
        coustaniView.inputType = InputType.TYPE_CLASS_NUMBER
        coustaniView.setText(thisRow.COUSTANI.toString())
        coustaniView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setCoustani(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(coustaniView)

        var mansoniaView = EditText(context)
        mansoniaView.inputType = InputType.TYPE_CLASS_NUMBER
        mansoniaView.setText(thisRow.MANSONIA.toString())
        mansoniaView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setMansonia(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(mansoniaView)

        var aedesView = EditText(context)
        aedesView.inputType = InputType.TYPE_CLASS_NUMBER
        aedesView.setText(thisRow.AEDES.toString())
        aedesView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setAedes(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(aedesView)

        var coquilettidiaView = EditText(context)
        coquilettidiaView.inputType = InputType.TYPE_CLASS_NUMBER
        coquilettidiaView.setText(thisRow.COQUILETTIDIA.toString())
        coquilettidiaView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setCoquilettidia(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(coquilettidiaView)

        var otherView = EditText(context)
        otherView.inputType = InputType.TYPE_CLASS_NUMBER
        otherView.setText(thisRow.OTHER.toString())
        otherView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                setOther(s.toString().toIntOrNull(), iRow)
            }
        })
        row.addView(otherView)
        return row
    }

    fun setGambiae(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).GAMBIAE = number
    }

    fun setCulex(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).CULEX = number
    }

    fun setFunestus(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).FUNESTUS = number
    }

    fun setCoustani(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).COUSTANI = number
    }

    fun setMansonia(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).MANSONIA = number
    }

    fun setAedes(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).AEDES = number
    }

    fun setCoquilettidia(number : Int?, iRow : Int)  {
        (this.dataArray[iRow] as HLCDataEntry).COQUILETTIDIA = number
    }

    fun setOther(number : Int?, iRow : Int)  {
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

            if (row.getChildAt(iCol) is EditText) {
                thisTableCell = thisTableCell as EditText
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

    override fun buildInfoRow(context: Context, sentResource: Int, completeResource: Int) : TableRow
    {
        var row = TableRow(context)
        var sentView = ImageView(context)
        sentView.setImageResource(sentResource);
        var formTypeView = TextView(context)

        formTypeView.setText(context.resources.getString(R.string.human_landing_catch))
        var projectCodeView = TextView(context)
        projectCodeView.setText(this.metaData.PROJECT_CODE)
        var dateCodeView = TextView(context)
        dateCodeView.setText(this.metaData.DATE)
        var completeView = ImageView(context)
        completeView.setImageResource(completeResource)

        row.addView(sentView)
        row.addView(formTypeView)
        row.addView(projectCodeView)
        row.addView(dateCodeView)
        row.addView(completeView)
        return row

    }


    override fun buildInfoHeader(context: Context) : TableRow
    {
        val row = TableRow(context)
        val sentView = TextView(context)
        sentView.setText(context.getString(R.string.sent))
        val formTypeView = TextView(context)
        formTypeView.setText(context.getString(R.string.form_type))
        val projectCodeView = TextView(context)
        projectCodeView.setText(context.getString(R.string.project_code))
        val dateCodeView = TextView(context)
        dateCodeView.setText(R.string.day_month_year)
         val completeView = TextView(context)
        completeView.setText(R.string.complete_form)

        row.addView(sentView)
        row.addView(formTypeView)
        row.addView(projectCodeView)
        row.addView(dateCodeView)
        row.addView(completeView)
        return row
    }
}