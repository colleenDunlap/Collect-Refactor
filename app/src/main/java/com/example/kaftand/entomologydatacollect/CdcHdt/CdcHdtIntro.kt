package com.example.kaftand.entomologydatacollect.CdcHdt

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KMutableProperty0

class CdcHdtIntro : LanguagePreservingActivity() {

    var metaData = CdcHdtMetaData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_cdc_hdt_intro)

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
            DatePickerDialog(this@CdcHdtIntro, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val projectCodeTextEdit = findViewById<EditText>(R.id.project_code)
        projectCodeTextEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.metaData::PROJECT_CODE)))
        val clusterNumberTextEdit = findViewById<EditText>(R.id.cluster_number_cdc)
        clusterNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::CLUSTER_NUMBER)))
        val volunteerNumberTextEdit = findViewById<EditText>(R.id.volunteer_number)
        volunteerNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::VOLUNTEER_NUMBER)))
        val serialTextEdit = findViewById<EditText>(R.id.serial)
        serialTextEdit.addTextChangedListener(createTextWatcherNonNullInt(createCallBackFor<Int>(this.metaData::serial)))
        val monthTextEdit = findViewById<EditText>(R.id.month)
        monthTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::MONTH)))
        val weekTextEdit = findViewById<EditText>(R.id.week)
        weekTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::WEEK)))
    }


    fun nextScreen(view: View) {
        try {
            var intent = Intent(this, CdcHdt::class.java)
            var bundle: Bundle = Bundle()
            bundle.putParcelable("MetaData", this.metaData)
            intent.putExtra("MetaData", bundle)
            startActivity(intent)
        } catch (e : Exception) {
            return
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