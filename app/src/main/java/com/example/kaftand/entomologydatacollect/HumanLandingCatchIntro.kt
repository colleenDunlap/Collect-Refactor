package com.example.kaftand.entomologydatacollect

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_human_landing_catch_intro.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HumanLandingCatchIntro : AppCompatActivity() {
    var originalDrawable: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_landing_catch_intro)
        setSupportActionBar(toolbar)
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
    }

    fun goToHLCGrid(view: View) {

        try {
            var hLCMeta = this.packHLCMetaData()
            var intent = Intent(this, HumanLandingCatch::class.java)
            var bundle: Bundle = Bundle()
            bundle.putParcelable("MetaBundle", hLCMeta)
            intent.putExtra("HLCMeta", bundle)
            startActivity(intent)
        } catch (e : Exception) {
            return
        }


    }

    fun packHLCMetaData() : HLCMetaData{
        var hLCMeta = HLCMetaData()
        hLCMeta.VILLAGE = getEditString(R.id.VillageEdit)
        hLCMeta.DATE = getEditStringFromView(R.id.DateEdit)
        hLCMeta.PROJECT_CODE = getEditString(R.id.ProjectCodeEdit)
        hLCMeta.HOUSE_NUMBER = getEditInt(R.id.HouseNumberEdit)
        hLCMeta.CLUSTER_NUMBER = getEditInt(R.id.ClusterNumberEdit)
        hLCMeta.VOLUNTEER_NUMBER_IN = getEditInt(R.id.VolunteerNumberInsideEdit)
        hLCMeta.VOLUNTEER_NUMBER_OUT = getEditInt(R.id.VolunteerNumberOutsideEdit)
        hLCMeta.IN_OR_OUT = "in"

        if ((hLCMeta.VILLAGE == null) || (hLCMeta.DATE == null) || (hLCMeta.PROJECT_CODE == null) || (hLCMeta.HOUSE_NUMBER == null) || (hLCMeta.CLUSTER_NUMBER == null) || (hLCMeta.VOLUNTEER_NUMBER_IN == null) || (hLCMeta.VOLUNTEER_NUMBER_OUT == null))
        {
            throw error("Missing Data")
        }
        return hLCMeta
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
