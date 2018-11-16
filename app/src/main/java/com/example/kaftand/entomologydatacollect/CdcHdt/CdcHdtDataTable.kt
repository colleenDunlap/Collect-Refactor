package com.example.kaftand.entomologydatacollect.CdcHdt

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.FormInterfaces.TabularData
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrialDataEntry
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.TableConstants
import kotlin.reflect.KMutableProperty0

class CdcHdtDataTable(override val metaData: CdcHdtMetaData, override val nRows: Int) : TabularData<CdcHdtDataEntry> {
    override var dataArray = ArrayList<CdcHdtDataEntry>()

    init {
        val trap_id_array = arrayListOf<String>("HDT (02)", "CDC (01)")
        for (iRow in 0 until nRows) {
            dataArray.add(CdcHdtDataEntry(this.metaData))
            val trap_id = if (iRow < 4) {trap_id_array[0]} else {trap_id_array[1]}
            dataArray[iRow].TRAP_ID = trap_id
        }
    }

    override fun getColNames(): ArrayList<String> {
        var colnames = ArrayList<String>()
        colnames.add(R.string.house_number.toString())
        colnames.add(R.string.trap_id.toString())
        colnames.add(R.string.unfed_alive.toString())
        colnames.add(R.string.fed_alive.toString())
        colnames.add(R.string.unfed_dead.toString())
        colnames.add(R.string.fed_dead.toString())
        colnames.add(R.string.unfed_alive.toString())
        colnames.add(R.string.fed_alive.toString())
        colnames.add(R.string.unfed_dead.toString())
        colnames.add(R.string.fed_dead.toString())
        colnames.add(R.string.unfed_alive.toString())
        colnames.add(R.string.fed_alive.toString())
        colnames.add(R.string.unfed_dead.toString())
        colnames.add(R.string.fed_dead.toString())
        colnames.add(R.string.total_female.toString())
        colnames.add("Genus")
        return colnames
    }

    override fun createRow(iRow: Int, context: Context): TableRow {
        val row = TableRow(context)
        var dataRow = this.dataArray.get(iRow)

        var hutNumberEdit = EditText(context)
        hutNumberEdit.inputType = InputType.TYPE_CLASS_NUMBER
        hutNumberEdit.setText(dataRow.HUT_NUMBER.toString())
        hutNumberEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::HUT_NUMBER)))
        row.addView(hutNumberEdit)

        var trapIdEdit = EditText(context)
        trapIdEdit.setText(dataRow.TRAP_ID.toString())
        trapIdEdit.isEnabled = false
        row.addView(trapIdEdit)

        val propertyArray = arrayListOf(this.dataArray[iRow]::ARABIENSIS_UNFED_ALIVE,
                this.dataArray[iRow]::ARABIENSIS_FED_ALIVE, this.dataArray[iRow]::ARABIENSIS_UNFED_DEAD,
                this.dataArray[iRow]::ARABIENSIS_FED_DEAD, this.dataArray[iRow]::FUNESTUS_UNFED_ALIVE,
                this.dataArray[iRow]::FUNESTUS_FED_ALIVE, this.dataArray[iRow]::FUNESTUS_UNFED_DEAD,
                this.dataArray[iRow]::FUNESTUS_FED_DEAD,this.dataArray[iRow]::CULEX_UNFED_ALIVE,
                this.dataArray[iRow]::CULEX_FED_ALIVE, this.dataArray[iRow]::CULEX_UNFED_DEAD,
                this.dataArray[iRow]::CULEX_FED_DEAD)

        var first = true
        var lastView : EditText? = null
        var iProperty = 0;
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
            if ((iProperty.rem(4) < 2) && (iRow < 4)) {
                countEdit.setText("0")
                countEdit.isEnabled = false
            }
            row.addView(countEdit)
            first = false
            iProperty++
        }

        var otherFemaleEdit = EditText(context)
        otherFemaleEdit.setText(dataRow.OTHER_FEMALE.toString())
        otherFemaleEdit.inputType = InputType.TYPE_CLASS_NUMBER
        otherFemaleEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::OTHER_FEMALE)))
        row.addView(otherFemaleEdit)

        var otherSpecies = EditText(context)
        otherSpecies.setText(dataRow.OTHER_FEMALE.toString())
        otherSpecies.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.dataArray[iRow]::OTHER_SPECIES)))
        row.addView(otherSpecies)

        return row
    }

    override fun buildInfoRow(context: Context, sentResource: Int, completeResource: Int): TableRow {
        var row = TableRow(context)
        var sentView = ImageView(context)
        sentView.setImageResource(sentResource);
        var formTypeView = TextView(context)

        formTypeView.setText(context.resources.getString(R.string.CDC_HDT))
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

}