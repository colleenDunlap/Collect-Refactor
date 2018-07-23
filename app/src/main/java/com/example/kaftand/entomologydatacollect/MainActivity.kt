package com.example.kaftand.entomologydatacollect

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var HLC : HumanLandingCatch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        englishRadio.setChecked(true)
    }

    fun setLocale(context: Context, language: String): Context {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)

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

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.getResources().getConfiguration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
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
        val context = this.setLocale(this, languageCode)
        val resources = context.getResources()

        newFormButton.setText(resources.getString(R.string.fill_out_form))
        uploadFormButton.setText(resources.getString(R.string.upload_form))
    }

}
