package com.example.kaftand.entomologydatacollect

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.net.ConnectivityManager
import android.util.Log
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.kaftand.entomologydatacollect.CdcHdt.CdcHdt
import com.example.kaftand.entomologydatacollect.CdcHdt.CdcHdtIntro
import com.example.kaftand.entomologydatacollect.ConeBioassay.ConeBioassayIntro
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HumanLandingCatch
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HumanLandingCatchIntro
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrial
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrialIntro
import com.example.kaftand.entomologydatacollect.IndoorRestingCollection.IndoorRestingCollectionIntro
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import java.text.FieldPosition


class MainActivity : LanguagePreservingActivity() {
    var HLC : HumanLandingCatch? = null
    var selectedForm = FormTypeKeys.HLC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lang = this.getSavedLang(this)
        if (lang.equals("en"))
        {
            englishRadio.isChecked = true
        } else if (lang.equals("sw"))
        {
            kiswahiliRadio.isChecked = true
        }

        val spinner = findViewById(R.id.spinner2) as Spinner
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.form_type_drop_down, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateSelectedView(parent as Spinner)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    fun updateSelectedView(spinner: Spinner) {

        if (spinner.selectedItem.toString() == getString(R.string.human_landing_catch)) {
            this.selectedForm = FormTypeKeys.HLC
        } else if (spinner.selectedItem.toString() == getString(R.string.hut_study)) {
            this.selectedForm = FormTypeKeys.HutTrial
        } else if (spinner.selectedItem.toString() == getString(R.string.CDC_HDT)){
            this.selectedForm = FormTypeKeys.CdcHdt
        } else if (spinner.selectedItem.toString() == getString(R.string.indoor_resting_collection)){
            this.selectedForm = FormTypeKeys.IndoorRestingCollection
        } else if (spinner2.selectedItem.toString() == getString(R.string.cone_bioassay)) {
            this.selectedForm = FormTypeKeys.ConeBioassay
        }

    }

    fun swahiliSelected(view: View) {
        updateViews(languageCode = "sw")
    }

    fun englishSelected(view: View)  {
        updateViews(languageCode = "en")
    }

    fun fillOutFormButtonClick(view: View) {
        if (spinner2.selectedItem.toString() == getString(R.string.human_landing_catch)) {
            startActivity(Intent(this, HumanLandingCatchIntro::class.java))
        } else if (spinner2.selectedItem.toString() == getString(R.string.hut_study)) {
            startActivity(Intent(this, HutTrialIntro::class.java))
        } else if (spinner2.selectedItem.toString() == getString(R.string.CDC_HDT)) {
            startActivity(Intent(this, CdcHdtIntro::class.java))
        } else if (spinner2.selectedItem.toString() == getString(R.string.indoor_resting_collection)) {
            startActivity(Intent(this, IndoorRestingCollectionIntro::class.java))
        } else if (spinner2.selectedItem.toString() == getString(R.string.cone_bioassay)) {
            startActivity(Intent(this, ConeBioassayIntro::class.java))
        }
    }


    fun uploadFormButtonClick(view: View) {
        if (this.checkForConnection())
        {
            var intent = Intent(this, UploadFile::class.java)
            var bundle = Bundle()
            bundle.putString("formString", this.selectedForm)
            intent.putExtra("FormType", bundle)
            startActivity(intent)
        } else {
            this.alertNoInternet()
        }
    }

    fun editFormButtonClick (view: View) {
        var intent = Intent(this, DataEditMenu::class.java)
        var bundle = Bundle()
        bundle.putString("formString", this.selectedForm)
        intent.putExtra("FormType", bundle)
        startActivity(intent)
    }


    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = baseContext.getResources().getConfiguration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return baseContext.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.getResources()

        val configuration = resources.getConfiguration()
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics())

        return context
    }

    private fun updateViews(languageCode: String) {
        this.saveLocale(languageCode)
        val context = this.setLocale(this, languageCode)
        val resources = context.getResources()

        newFormButton.setText(resources.getString(R.string.fill_out_form))
        uploadFormButton.setText(resources.getString(R.string.upload_form))
    }

    private fun checkForConnection(): Boolean
    {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected))
    }

    private fun alertNoInternet()
    {
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(getString(R.string.no_internet))
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }

}
