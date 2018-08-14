package com.example.kaftand.entomologydatacollect

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import android.preference.PreferenceManager
import android.content.res.Configuration


open class LanguagePreservingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lang = this.getSavedLang(this)
        this.setLocale(this, lang)

    }

    override fun attachBaseContext(newBase: Context) {
        // .. create or get your new Locale object here.
        var lang = getSavedLang(newBase)
        val newLocale: Locale = Locale(lang)
        val context = ContextWrapper.wrap(newBase, newLocale)
        super.attachBaseContext(context)
    }

    fun getSavedLang(context: Context) : String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var lang = preferences.getString("lang", "")
        if (lang!!.equals("", ignoreCase = true)) {

            lang = "en"
        }
        return lang
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        val lang = getSavedLang(this)
        this.setLocale(this, lang)
    }

    fun saveLocale(lang : String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("lang", lang)
        editor.apply()
    }

    fun setLocale(context: Context, language: String): Context {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)

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
        val context = this.setLocale(this, languageCode)
        val resources = context.getResources()

        newFormButton.setText(resources.getString(R.string.fill_out_form))
        uploadFormButton.setText(resources.getString(R.string.upload_form))
    }

}