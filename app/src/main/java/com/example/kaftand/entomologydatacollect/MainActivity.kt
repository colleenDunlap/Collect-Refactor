package com.example.kaftand.entomologydatacollect

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import android.content.DialogInterface
import android.content.res.Configuration
import android.support.v7.app.AlertDialog
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.kaftand.entomologydatacollect.R.id.englishRadio
import com.example.kaftand.entomologydatacollect.R.id.kiswahiliRadio


class MainActivity : LanguagePreservingActivity() {
    var HLC : HumanLandingCatch? = null

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
    }


    fun swahiliSelected(view: View) {
        updateViews(languageCode = "sw")
    }

    fun englishSelected(view: View)  {
        updateViews(languageCode = "en")
    }

    fun fillOutFormButtonClick(view: View) {
        startActivity( Intent(this, HumanLandingCatchIntro::class.java))
    }


    fun uploadFormButtonClick(view: View) {
        if (this.checkForConnection())
        {
            startActivity( Intent(this, UploadFile::class.java))
        } else {
            this.alertNoInternet()
        }
    }

    fun editFormButtonClick (view: View) {
        startActivity(Intent(this, DataEditMenu::class.java))
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
