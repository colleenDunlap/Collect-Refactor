package com.example.kaftand.entomologydatacollect.IndoorRestingCollection

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import com.example.kaftand.entomologydatacollect.Util.checkMissingDataInLayout
import com.example.kaftand.entomologydatacollect.Util.formCountTracker
import com.example.kaftand.entomologydatacollect.Util.volunteerNameTracker
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates
import kotlin.reflect.KMutableProperty0


class IndoorRestingCollectionIntro : LanguagePreservingActivity() {
    var responsibleForData = false
    var metaData: IndoorRestingCollectionMetaData by Delegates.notNull()
    var dataTable: IndoorRestingCollectionDataTable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        metaData = IndoorRestingCollectionMetaData(formCountTracker.getSerialNumber(this@IndoorRestingCollectionIntro, FormTypeKeys.IndoorRestingCollection))
        setContentView(R.layout.content_indoor_resting_collection_intro)
        this.metaData.count = formCountTracker.readFormCount(metaData.formType, this)

        val textView: TextView = findViewById(R.id.DateEdit)
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)
            this.metaData.DATE = sdf.format(cal.time)
        }
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        this.metaData.DATE = sdf.format(cal.time)

        textView.setOnClickListener {
            DatePickerDialog(this@IndoorRestingCollectionIntro, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val projectCodeTextEdit = findViewById<EditText>(R.id.project_code)
        projectCodeTextEdit.setText(this.metaData.PROJECT_CODE)
        projectCodeTextEdit.isEnabled = false
        val clusterNumberTextEdit = findViewById<EditText>(R.id.cluster_number_cdc)
        clusterNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::CLUSTER_NUMBER)))
        val volunteerNumberTextEdit = findViewById<AutoCompleteTextView>(R.id.volunteer_number)
        volunteerNumberTextEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.metaData::VOLUNTEER)))
        val monthTextEdit = findViewById<EditText>(R.id.month)
        monthTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::MONTH)))
        val weekTextEdit = findViewById<EditText>(R.id.week)
        weekTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::WEEK)))

        val spinner = findViewById(R.id.village_spinner) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.villages_drop_down, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateVillage(parent as Spinner)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val volunteerAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, volunteerNameTracker.readVolunteerNames(this))
        volunteerNumberTextEdit.setAdapter(volunteerAdapter)
    }


    fun updateVillage(spinner: Spinner) {
        if(R.string.tap_to_enter.toString() == spinner.selectedItem.toString()) {
            this.metaData.VILLAGE = null
        }
        this.metaData.VILLAGE = spinner.selectedItem.toString()
    }


    fun nextScreen(view: View) {
        try {
            if (!checkMissingDataInLayout.isDataMissingSetError(findViewById(R.id.main_container))) {
                if ((this.metaData.VOLUNTEER != null) and (this.metaData.VOLUNTEER != "") ){
                    volunteerNameTracker.appendNameIfUnique(this.metaData.VOLUNTEER!!, this)
                }
                var intent = Intent(this, IndoorRestingCollection::class.java)
                var bundle: Bundle = Bundle()
                if (responsibleForData) {
                    val gson = Gson()
                    updateDataTableMeta()
                    bundle.putString("DataTableString", gson.toJson(this.dataTable!!, IndoorRestingCollectionDataTable::class.java))
                    intent.putExtra("DataTableBundle",bundle)
                    startActivityForResult(intent, 1)
                } else {
                    bundle.putParcelable("MetaData", this.metaData)
                    intent.putExtra("MetaData", bundle)
                    startActivityForResult(intent, 1)
                }
            }
        } catch (e : Exception) {
            return
        }
    }

    fun updateDataTableMeta() {
        for (iRow in 0 until this.dataTable!!.dataArray.size) {
            this.dataTable!!.dataArray[iRow].updateFromMetaData(this.metaData)
        }
        this.dataTable!!.metaData = this.metaData
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val gson = Gson()
                this.dataTable = gson.fromJson(data!!.getStringExtra("result"), IndoorRestingCollectionDataTable::class.java)
                this.responsibleForData = true
            }
        } else {
            finish()
        }
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

    fun createTextWatcherNonNullInt(function : (Int) -> (Unit)) : TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val output = if (s.toString().toIntOrNull() == null) {0} else {s.toString().toInt()}
                function(output)
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