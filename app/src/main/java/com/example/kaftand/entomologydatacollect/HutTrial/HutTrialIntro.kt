package com.example.kaftand.entomologydatacollect.HutTrial

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HumanLandingCatch
import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.R
import java.text.SimpleDateFormat
import java.util.*

class HutTrialIntro : LanguagePreservingActivity() {
    var metaData = HutTrialMetaData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_hut_trial_intro)

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
            DatePickerDialog(this@HutTrialIntro, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val projectCodeTextEdit = findViewById<EditText>(R.id.project_code)
        projectCodeTextEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                updateProjectCode(s.toString())
            }
        })

        val numberOfHutsTextEdit = findViewById<EditText>(R.id.number_of_huts)
        numberOfHutsTextEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val n_huts =  if(s.toString().toIntOrNull() == null) {0} else {s.toString().toInt()}
                updateNumberOfHuts(n_huts)

            }
        })
    }

    fun updateProjectCode(projectCode: String) {
        this.metaData.PROJECT_CODE = projectCode
    }

    fun updateNumberOfHuts(numberOfHuts: Int) {
        this.metaData.N_HUTS = numberOfHuts
    }

    fun nextScreen(view: View) {
        try {
            var intent = Intent(this, HutTrial::class.java)
            var bundle: Bundle = Bundle()
            bundle.putParcelable("MetaData", this.metaData)
            intent.putExtra("MetaData", bundle)
            startActivity(intent)
        } catch (e : Exception) {
            return
        }
    }

}