package com.example.kaftand.entomologydatacollect.HutTrial

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.FormInterfaces.TabularData
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.TableConstants
import java.lang.Math.floor
import kotlin.reflect.KMutableProperty0

class HutTrialDataTable(override val metaData: HutTrialMetaData, override val nRows: Int) : TabularData<HutTrialDataEntry> {

    override var dataArray: ArrayList<HutTrialDataEntry> = ArrayList<HutTrialDataEntry>()

    init {
        val trap_id_array = arrayListOf<String>("NET", "NDANI", "MITEGO")
        for (iRow in 0 until nRows) {
            val iHut = 1 + floor(iRow / 3.0).toInt()
            dataArray.add(HutTrialDataEntry(this.metaData))
            dataArray[iRow].TRAP_ID = trap_id_array[iRow % 3]
            dataArray[iRow].HUT_NUMBER = iHut
        }
    }


    override fun getColNames(): ArrayList<String> {
        return arrayListOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
                "17","18","19","20","21","22","23","24","25","26","27","28","29","30") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRow(iRow: Int, context: Context): TableRow {
        val row = TableRow(context)
        var dataRow = this.dataArray.get(iRow)
        val hutNumberEdit = EditText(context)
        hutNumberEdit.inputType = InputType.TYPE_CLASS_NUMBER
        hutNumberEdit.setText(dataRow.HUT_NUMBER.toString())
        hutNumberEdit.setTextColor(ContextCompat.getColor(context, R.color.black))
        hutNumberEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::HUT_NUMBER)))
        row.addView(hutNumberEdit)

        val trapIdView = EditText(context)
        trapIdView.textSize = 6f
        trapIdView.setText(dataRow.TRAP_ID.toString())
        trapIdView.isEnabled = false
        trapIdView.setTextColor(ContextCompat.getColor(context, R.color.black))
        row.addView(trapIdView)

        val netNumberEdit = EditText(context)
        netNumberEdit.inputType = InputType.TYPE_CLASS_NUMBER
        netNumberEdit.textSize = 10f
        if (dataRow.NET_NUMBER == null) {
            netNumberEdit.setText("")
        } else {
            netNumberEdit.setText(dataRow.NET_NUMBER.toString())
        }
        netNumberEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::NET_NUMBER)))
        row.addView(netNumberEdit)

        val volunteerNumberEdit = EditText(context)
        volunteerNumberEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if(dataRow.VOLUNTEER_NUMBER == null) {
            volunteerNumberEdit.setText("")
        } else {
            volunteerNumberEdit.setText(dataRow.VOLUNTEER_NUMBER.toString())
        }
        volunteerNumberEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::VOLUNTEER_NUMBER)))
        row.addView(volunteerNumberEdit)

        val propertyArray = arrayListOf(this.dataArray[iRow]::GAMBIAE_MORNING_UNFED_ALIVE,
                this.dataArray[iRow]::GAMBIAE_MORNING_FED_ALIVE, this.dataArray[iRow]::GAMBIAE_MORNING_UNFED_DEAD,
                this.dataArray[iRow]::GAMBIAE_MORNING_FED_DEAD, this.dataArray[iRow]::GAMBIAE_24HR_UNFED_ALIVE,
                this.dataArray[iRow]::GAMBIAE_24HR_FED_ALIVE, this.dataArray[iRow]::GAMBIAE_24HR_UNFED_DEAD,
                this.dataArray[iRow]::GAMBIAE_24HR_FED_DEAD, this.dataArray[iRow]::FUNESTUS_MORNING_UNFED_ALIVE,
                this.dataArray[iRow]::FUNESTUS_MORNING_FED_ALIVE, this.dataArray[iRow]::FUNESTUS_MORNING_UNFED_DEAD,
                this.dataArray[iRow]::FUNESTUS_MORNING_FED_DEAD, this.dataArray[iRow]::FUNESTUS_24HR_UNFED_ALIVE,
                this.dataArray[iRow]::FUNESTUS_24HR_FED_ALIVE, this.dataArray[iRow]::FUNESTUS_24HR_UNFED_DEAD,
                this.dataArray[iRow]::FUNESTUS_24HR_FED_DEAD, this.dataArray[iRow]::CULEX_MORNING_UNFED_ALIVE,
                this.dataArray[iRow]::CULEX_MORNING_FED_ALIVE, this.dataArray[iRow]::CULEX_MORNING_UNFED_DEAD,
                this.dataArray[iRow]::CULEX_MORNING_FED_DEAD, this.dataArray[iRow]::CULEX_24HR_UNFED_ALIVE,
                this.dataArray[iRow]::CULEX_24HR_FED_ALIVE, this.dataArray[iRow]::CULEX_24HR_UNFED_DEAD,
                this.dataArray[iRow]::CULEX_24HR_FED_DEAD)
        var first = true
        var lastView : EditText? = null
        for (eachProperty in propertyArray) {

            val countEdit = EditText(context)
            if (first) {
                countEdit.id = TableConstants.firstEntry(iRow)
            }
            countEdit.inputType = InputType.TYPE_CLASS_NUMBER
            val textString = if(eachProperty.get() == null) {""} else {eachProperty.get().toString()}
            countEdit.setText(textString)
            countEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(eachProperty)))
            countEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            if (!first) {
                lastView!!.nextFocusForwardId = countEdit.id
            }
            lastView = countEdit
            row.addView(countEdit)
            first = false
        }

        val totalCountEditText = EditText(context)
        totalCountEditText.inputType = InputType.TYPE_CLASS_NUMBER
        var totalString = if(dataRow.OTHER_FEMALE_COUNT == null) {""} else {dataRow.OTHER_FEMALE_COUNT.toString()}
        totalCountEditText.setText(totalString)
        totalCountEditText.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::OTHER_FEMALE_COUNT)))
        row.addView(totalCountEditText)

        val genusEditText = EditText(context)
        totalString = if(dataRow.OTHER_GENUS == null) {""} else {dataRow.OTHER_GENUS.toString()}
        genusEditText.inputType = InputType.TYPE_CLASS_NUMBER
        genusEditText.setText(totalString)
        genusEditText.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::OTHER_GENUS)))
        row.addView(genusEditText)
        genusEditText.id = TableConstants.lastEntry(iRow)

        return row
    }

    override fun checkMissingData(iRow: Int, row: TableRow, missingDataError: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun <dataT>createCallBackFor(property: KMutableProperty0<dataT>) : (dataT) -> (Unit) {
        val callback = fun(count: dataT): Unit { property.set(count)}
        return callback
    }

    fun createTextWatcherInt(function : (Int?) -> (Unit)) : TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                function(s.toString().toIntOrNull())
            }
        }
    }
    fun createTextWatcherString(function : (String?) -> (Unit)) : TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                function(s.toString())
            }
        }
    }

    override fun buildInfoRow(context: Context, sentResource: Int, completeResource: Int) : TableRow
    {
        var row = TableRow(context)
        var sentView = ImageView(context)
        sentView.setImageResource(sentResource);
        var formTypeView = TextView(context)

        formTypeView.setText(context.resources.getString(R.string.hut_study))
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