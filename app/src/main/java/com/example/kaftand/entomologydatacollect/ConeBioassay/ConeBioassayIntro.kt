package com.example.kaftand.entomologydatacollect.ConeBioassay

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


class ConeBioassayIntro : LanguagePreservingActivity() {

    var metaData = ConeBioassayMetaData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_cone_bioassay_intro)

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
        houseNumberTextEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.metaData::IRS_CODE)))

        val temperatureTextEdit = findViewById<EditText>(R.id.temperature)
        houseNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::TEMPERATURE)))

        val humidityTextEdit = findViewById<EditText>(R.id.humidity)
        houseNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::HUMIDITY)))

        val colonyTextEdit = findViewById<EditText>(R.id.colony)
        houseNumberTextEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.metaData::MOSQUITO_STRAIN)))

        val ageMinTextEdit = findViewById<EditText>(R.id.age_min)
        houseNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::MOSQUITO_AGE_MIN)))

        val ageMaxTextEdit = findViewById<EditText>(R.id.age_max)
        houseNumberTextEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.metaData::MOSQUITO_AGE_MAX)))
    }


    fun nextScreen(view: View) {
        try {
            var intent = Intent(this, ConeBioassay::class.java)
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