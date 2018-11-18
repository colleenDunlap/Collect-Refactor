package com.example.kaftand.entomologydatacollect.HumanLandingCatch

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.kaftand.entomologydatacollect.CdcHdt.CdcHdt
import com.example.kaftand.entomologydatacollect.CdcHdt.CdcHdtDataTable
import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.formCountTracker
import com.example.kaftand.entomologydatacollect.Util.volunteerNameTracker
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_human_landing_catch_intro.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HumanLandingCatchIntro : LanguagePreservingActivity() {
    var originalDrawable: Drawable? = null
    var metaData = HLCMetaData()
    var responsibleForData = false
    var dataTable: HLCDataTable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_landing_catch_intro)
        setSupportActionBar(toolbar)
        this.metaData.count = formCountTracker.readFormCount(metaData.formType, this)
        // taken from https://stackoverflow.com/questions/45842167/how-to-use-datepickerdialog-in-kotlin
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

        }

        textView.setOnClickListener {
            DatePickerDialog(this@HumanLandingCatchIntro, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        this.originalDrawable = findViewById<EditText>(R.id.ClusterNumberEdit).getBackground()
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

        val volunteerInNumberTextEdit = findViewById<AutoCompleteTextView>(R.id.VolunteerNumberInsideEdit)
        val volunteerOutNumberTextEdit = findViewById<AutoCompleteTextView>(R.id.VolunteerNumberOutsideEdit)

        val volunteerAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, volunteerNameTracker.readVolunteerNames(this))
        volunteerOutNumberTextEdit.setAdapter(volunteerAdapter)
        volunteerInNumberTextEdit.setAdapter(volunteerAdapter)
    }


    fun updateVillage(spinner: Spinner) {
        if(R.string.tap_to_enter.toString() == spinner.selectedItem.toString()) {
            this.metaData.VILLAGE = null
        }
        this.metaData.VILLAGE = spinner.selectedItem.toString()
    }

    fun goToHLCGrid(view: View) {
        try {
            var hLCMeta = this.packHLCMetaData()
            var intent = Intent(this, HumanLandingCatch::class.java)
            var bundle: Bundle = Bundle()
            volunteerNameTracker.appendNameIfUnique(hLCMeta.VOLUNTEER_IN!!, this)
            volunteerNameTracker.appendNameIfUnique(hLCMeta.VOLUNTEER_OUT!!, this)
            if (responsibleForData) {
                val gson = Gson()
                updateDataTableMeta()
                bundle.putString("DataTableString", gson.toJson(this.dataTable!!, HLCDataTable::class.java))
                intent.putExtra("DataTableBundle",bundle)
                startActivityForResult(intent, 1)
            } else {

                bundle.putParcelable("MetaBundle", hLCMeta)
                intent.putExtra("HLCMeta", bundle)
                startActivityForResult(intent, 1)
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

    fun packHLCMetaData() : HLCMetaData {
        metaData.DATE = getEditStringFromView(R.id.DateEdit)
        metaData.PROJECT_CODE = getEditString(R.id.ProjectCodeEdit)
        metaData.HOUSE_NUMBER = getEditInt(R.id.HouseNumberEdit)
        metaData.CLUSTER_NUMBER = getEditInt(R.id.ClusterNumberEdit)
        metaData.VOLUNTEER_IN = getEditString(R.id.VolunteerNumberInsideEdit)
        metaData.VOLUNTEER_OUT = getEditString(R.id.VolunteerNumberOutsideEdit)

        if ((metaData.VILLAGE == null) || (metaData.DATE == null) || (metaData.PROJECT_CODE == null) || (metaData.HOUSE_NUMBER == null) || (metaData.CLUSTER_NUMBER == null) || (metaData.VOLUNTEER_IN == null) || (metaData.VOLUNTEER_OUT == null))
        {
            throw error("Missing Data")
        }
        return metaData
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            try {
                val gson = Gson()
                this.dataTable = gson.fromJson(data!!.getStringExtra("result"), HLCDataTable::class.java)
                this.metaData = data!!.getParcelableExtra("metaData")
                this.responsibleForData = true
            } catch (e: Exception){
                finish()
            }
        } else {
            finish()
        }
    }

    fun getEditInt(id: Int) : Int? {
        var editText = findViewById(id) as EditText
        var editNumber = editText.getText().toString().toIntOrNull()
        if (editNumber == null)
        {
            editText.setError(getString(R.string.missing_data))
        }
        return editText.getText().toString().toIntOrNull()
    }

    fun getEditString(id: Int) : String? {
        var editText = findViewById(id) as EditText
        var editString : String? = editText.getText().toString()
        if (editString == "")
        {
            editString =  null
        }
        if (editString == null)
        {
            editText.setError(getString(R.string.missing_data))
        }
        return editString
    }

    fun getEditStringFromView(id: Int) : String? {
        var editText = findViewById(id) as TextView
        var editString : String = editText.getText().toString()
        var dateStrings = editString.split(".")
        var sqlDateTime = dateStrings[2] + "-" + dateStrings[1] + "-" +  dateStrings[0] + "00:00:00"
        return editString
    }



}
