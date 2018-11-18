package com.example.kaftand.entomologydatacollect.ConeBioassay

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.example.kaftand.entomologydatacollect.FormInterfaces.TabularData
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.TableConstants
import kotlin.reflect.KMutableProperty0

class ConeBioassayDataTable(override var metaData: ConeBioassayMetaData, override val nRows: Int) : TabularData<ConeBioassayDataEntry> {
    override var dataArray = ArrayList<ConeBioassayDataEntry>()
    val INPUT_TYPE_TIME = 36
    init {
        val replicate_array = arrayListOf<Int>(1,2,3,4,5)
        for (iRow in 0 until nRows) {
            dataArray.add(ConeBioassayDataEntry(this.metaData))
            dataArray[iRow].REPLICATE = replicate_array[iRow]
        }
    }

    override fun getColNames(context: Context): ArrayList<String> {
        var colnames = ArrayList<String>()
        colnames.add(R.string.replicate.toString())
        colnames.add(R.string.exposure_start_hh_mm.toString())
        colnames.add(R.string.exposure_end_hh_mm.toString())
        colnames.add(R.string.n_mosquitoes_exposed.toString())
        colnames.add(R.string.alive.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add(R.string.dead.toString())

        return colnames
    }

    override fun createRow(iRow: Int, context: Context): TableRow {
        val row = TableRow(context)
        var dataRow = this.dataArray.get(iRow)

        var replicateEdit = EditText(context)
        replicateEdit.setText(dataRow.REPLICATE.toString())
        replicateEdit.isEnabled = false
        row.addView(replicateEdit)

        var exposureStartTimePicker = EditText(context)
        exposureStartTimePicker.setText(dataRow.EXPOSURE_START)
        exposureStartTimePicker.inputType = INPUT_TYPE_TIME
        exposureStartTimePicker.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.dataArray[iRow]::EXPOSURE_START)))
        row.addView(exposureStartTimePicker)

        var exposureEndTimePicker = EditText(context)
        exposureEndTimePicker.setText(dataRow.EXPOSURE_END)
        exposureEndTimePicker.inputType = INPUT_TYPE_TIME
        exposureEndTimePicker.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.dataArray[iRow]::EXPOSURE_END)))
        row.addView(exposureEndTimePicker)


        val propertyArray = arrayListOf(this.dataArray[iRow]::N_EXPOSED,
                this.dataArray[iRow]::A1, this.dataArray[iRow]::M1,
                this.dataArray[iRow]::A24, this.dataArray[iRow]::M24,
                this.dataArray[iRow]::A48, this.dataArray[iRow]::M48,
                this.dataArray[iRow]::A72, this.dataArray[iRow]::M72,
                this.dataArray[iRow]::A96, this.dataArray[iRow]::M96,
                this.dataArray[iRow]::A120, this.dataArray[iRow]::M120)

        var first = true
        var lastView: EditText? = null
        var iProperty = 0;
        for (eachProperty in propertyArray) {

            val countEdit = EditText(context)
            if (first) {
                countEdit.id = TableConstants.firstEntry(iRow)
            }
            countEdit.inputType = InputType.TYPE_CLASS_NUMBER
            val textString = if (eachProperty.get() == null) {
                ""
            } else {
                eachProperty.get().toString()
            }
            countEdit.setText(textString)
            countEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(eachProperty)))
            countEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            if (!first) {
                lastView!!.nextFocusForwardId = countEdit.id
            }
            lastView = countEdit
            row.addView(countEdit)
            first = false
            iProperty++
        }

        return row
    }

    override fun buildInfoRow(context: Context, sentResource: Int, completeResource: Int): TableRow {
        var row = TableRow(context)
        var sentView = ImageView(context)
        sentView.setImageResource(sentResource);
        var formTypeView = TextView(context)

        formTypeView.setText(context.resources.getString(R.string.indoor_resting_collection))
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

    override fun buildInfoHeader(context: Context): TableRow {
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

    override fun checkMissingData(iRow: Int, row: TableRow, missingDataError: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun <dataT> createCallBackFor(property: KMutableProperty0<dataT>): (dataT) -> (Unit) {
        val callback = fun(count: dataT): Unit { property.set(count) }
        return callback
    }

    fun createTextWatcherInt(function: (Int?) -> (Unit)): TextWatcher {
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

    fun createTextWatcherString(function: (String?) -> (Unit)): TextWatcher {
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
}