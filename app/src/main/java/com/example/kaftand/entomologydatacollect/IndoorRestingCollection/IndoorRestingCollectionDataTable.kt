package com.example.kaftand.entomologydatacollect.IndoorRestingCollection

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
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.TableConstants
import kotlin.reflect.KMutableProperty0

class IndoorRestingCollectionDataTable(override var metaData: IndoorRestingCollectionMetaData, override val nRows: Int) : TabularData<IndoorRestingCollectionDataEntry> {
    override var dataArray = ArrayList<IndoorRestingCollectionDataEntry>()

    init {
        val trap_id_array = arrayListOf<String>("ndani hajala",
                "ndani amekula", "ndani hajala", "ndani amekula")
        for (iRow in 0 until nRows) {
            dataArray.add(IndoorRestingCollectionDataEntry(this.metaData))
            dataArray[iRow].TRAP_ID = trap_id_array[iRow]
        }
    }

    override fun getColNames(context: Context): ArrayList<String> {
        var colnames = ArrayList<String>()
        colnames.add(R.string.hut_number.toString())
        colnames.add(R.string.trap_id.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add("24 " + R.string.mortality.toString())
        colnames.add("48 " + R.string.mortality.toString())
        colnames.add("72 " + R.string.mortality.toString())
        colnames.add("96 " + R.string.mortality.toString())
        colnames.add("120 " + R.string.mortality.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add("24 " + R.string.mortality.toString())
        colnames.add("48 " + R.string.mortality.toString())
        colnames.add("72 " + R.string.mortality.toString())
        colnames.add("96 " + R.string.mortality.toString())
        colnames.add("120 " + R.string.mortality.toString())
        colnames.add(R.string.dead.toString())
        colnames.add(R.string.alive.toString())
        colnames.add("24 " + R.string.mortality.toString())
        colnames.add("48 " + R.string.mortality.toString())
        colnames.add("72 " + R.string.mortality.toString())
        colnames.add("96 " + R.string.mortality.toString())
        colnames.add("120 " + R.string.mortality.toString())
        colnames.add("Total Female")
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
        trapIdEdit.setTextColor(context.resources.getColor(R.color.black))
        row.addView(trapIdEdit)

        val propertyArray = arrayListOf(this.dataArray[iRow]::ARABIENSIS_ALIVE,
                this.dataArray[iRow]::ARABIENSIS_DEAD, this.dataArray[iRow]::ARABIENSIS_M24,
                this.dataArray[iRow]::ARABIENSIS_M48, this.dataArray[iRow]::ARABIENSIS_M72,
                this.dataArray[iRow]::ARABIENSIS_M96, this.dataArray[iRow]::ARABIENSIS_M120,
                this.dataArray[iRow]::FUNESTUS_ALIVE, this.dataArray[iRow]::FUNESTUS_DEAD,
                this.dataArray[iRow]::FUNESTUS_M24, this.dataArray[iRow]::FUNESTUS_M48,
                this.dataArray[iRow]::FUNESTUS_M72, this.dataArray[iRow]::FUNESTUS_M96,
                this.dataArray[iRow]::FUNESTUS_M120,
                this.dataArray[iRow]::CULEX_ALIVE, this.dataArray[iRow]::CULEX_DEAD,
                this.dataArray[iRow]::CULEX_M24, this.dataArray[iRow]::CULEX_M48,
                this.dataArray[iRow]::CULEX_M72, this.dataArray[iRow]::CULEX_M96,
                this.dataArray[iRow]::CULEX_M120)

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

        var otherFemaleEdit = EditText(context)
        otherFemaleEdit.setText(dataRow.OTHER_FEMALE.toString())
        otherFemaleEdit.inputType = InputType.TYPE_CLASS_NUMBER
        otherFemaleEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.dataArray[iRow]::OTHER_FEMALE)))
        row.addView(otherFemaleEdit)

        var otherSpecies = EditText(context)
        otherSpecies.setText(dataRow.OTHER_FEMALE.toString())
        otherSpecies.inputType = InputType.TYPE_CLASS_TEXT
        otherSpecies.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.dataArray[iRow]::OTHER_SPECIES)))
        otherSpecies.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        row.addView(otherSpecies)

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