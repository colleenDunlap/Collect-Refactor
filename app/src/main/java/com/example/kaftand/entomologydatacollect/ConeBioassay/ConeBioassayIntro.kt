package com.example.kaftand.entomologydatacollect.ConeBioassay

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
import com.example.kaftand.entomologydatacollect.Util.checkMissingDataInLayout
import com.example.kaftand.entomologydatacollect.Util.formCountTracker
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KMutableProperty0


class ConeBioassayIntro : LanguagePreservingActivity() {
    var responsibleForData = false
    var dataTable: ConeBioassayDataTable? = null
    var metaData = ConeBioassayMetaData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_cone_bioassay_intro)
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
            DatePickerDialog(this@ConeBioassayIntro, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val houseNumberTextEdit = findViewById<EditText>(R.id.house_number)
        houseNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::HOUSE_NUMBER)))

        val irsCodeTextEdit = findViewById<EditText>(R.id.irs_code)
        irsCodeTextEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.metaData::IRS_CODE)))

        val temperatureTextEdit = findViewById<EditText>(R.id.temperature)
        temperatureTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::TEMPERATURE)))

        val humidityTextEdit = findViewById<EditText>(R.id.humidity)
        humidityTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::HUMIDITY)))

        val clusterNumberEdit = findViewById<EditText>(R.id.cluster_number)
        clusterNumberEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::CLUSTER_NUMBER)))

        val colonyTextEdit = findViewById<EditText>(R.id.colony)
        colonyTextEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.metaData::MOSQUITO_STRAIN)))

        val ageMinTextEdit = findViewById<EditText>(R.id.age_min)
        ageMinTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::MOSQUITO_AGE_MIN)))

        val ageMaxTextEdit = findViewById<EditText>(R.id.age_max)
        ageMaxTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::MOSQUITO_AGE_MAX)))

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
                var intent = Intent(this, ConeBioassay::class.java)
                var bundle: Bundle = Bundle()
                if (responsibleForData) {
                    val gson = Gson()
                    updateDataTableMeta()
                    bundle.putString("DataTableString", gson.toJson(this.dataTable!!, ConeBioassayDataTable::class.java))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val gson = Gson()
                this.dataTable = gson.fromJson(data!!.getStringExtra("result"), ConeBioassayDataTable::class.java)
                this.responsibleForData = true
            }
        } else {
            finish()
        }
    }


    fun updateDataTableMeta() {
        for (iRow in 0 until this.dataTable!!.dataArray.size) {
            this.dataTable!!.dataArray[iRow].updateFromMetaData(this.metaData)
        }
        this.dataTable!!.metaData = this.metaData
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