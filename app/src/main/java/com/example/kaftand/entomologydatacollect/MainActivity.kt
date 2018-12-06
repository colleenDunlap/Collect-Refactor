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
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kaftand.entomologydatacollect.CdcHdt.*
import com.example.kaftand.entomologydatacollect.ConeBioassay.*
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.*
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrial
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrialIntro
import com.example.kaftand.entomologydatacollect.IndoorRestingCollection.*
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import com.example.kaftand.entomologydatacollect.Util.ServerConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.FieldPosition
import kotlin.properties.Delegates


class MainActivity : LanguagePreservingActivity(), ViewFormFromServerDialog.Returning {

    var alertDialog: AlertDialog by Delegates.notNull<AlertDialog>()
    val gson = Gson()
    var selectedForm = FormTypeKeys.HLC

    override fun return_value(username: String, password: String, serial: String) {
        alertDialog.setMessage("LOADING...")
        alertDialog.show()
        sendRequest(username, password, serial)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alertDialog = AlertDialog.Builder(this@MainActivity).create()
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
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        spinner.setSelection(preferences.getInt("selectedFormOnMainPage", 0))
        updateSelectedView(spinner)
    }


    fun updateSelectedView(spinner: Spinner) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        with (preferences.edit()) {
            putInt("selectedFormOnMainPage", spinner.selectedItemPosition)
            commit()
        }
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

    fun loadFormForView(view: View) {
        if (checkForConnection()) {
            val dialog = ViewFormFromServerDialog.newInstance(selectedForm)
            dialog.show(supportFragmentManager, "ViewForm")
        } else {
            alertNoInternet()
        }

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

    fun createTextWatcherString(function: (String) -> (Unit)): TextWatcher {
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

    fun sendRequest (username: String, password: String, serial: String) {

        val requestQueue = Volley.newRequestQueue(this)
        val uRL = "${ServerConfig.IP_PORT}/${ServerConfig.RAW_DATA_EXTENSION}/${this.selectedForm}?" +
                "serial=${serial}"


        val stringRequest = object : StringRequest(Request.Method.GET, uRL, object : Response.Listener<String> {
            override fun onResponse(response: String) {
                Log.i("VOLLEY", response)
                if (response == "[]") {
                    missingDataForSerialAlert(serial).show()
                    return
                } else if (response == "{\"success\":false,\"message\":\"incorrect token\"}") {
                    val incorrectPasswordAlertDialog = AlertDialog.Builder(this@MainActivity).create()
                    incorrectPasswordAlertDialog.setMessage("Incorrect Username or Password")
                    incorrectPasswordAlertDialog.show()
                    this@MainActivity.alertDialog.dismiss()
                    return
                }
                try {
                    if ((response == "304") or (response == "200")) {
                        return
                    }
                    if (selectedForm == FormTypeKeys.CdcHdt) {
                        viewCDC(response)
                    } else if (selectedForm == FormTypeKeys.IndoorRestingCollection) {
                        viewIndoorRestingCollection(response)
                    } else if (selectedForm == FormTypeKeys.HLC) {
                        viewHLC(response)
                    } else if (selectedForm == FormTypeKeys.ConeBioassay) {
                        viewConeBioassay(response)
                    }
                    //getDialog().cancel()
                } catch (err : Error) {
                    this@MainActivity.alertDialog.dismiss()
                }
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.e("VOLLEY", error.toString())
                this@MainActivity.alertDialog.dismiss()
            }
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            //This is for Headers If You Needed
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json; charset=UTF-8"
                params["username"] = username
                params["password"] = password
                return params
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                var responseString = ""
                if (response != null) {
                    responseString = String(response.data)
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response))
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(50000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(stringRequest);
    }

    private fun viewConeBioassay(response: String) {
        var coneBioassayData = gson.fromJson<ArrayList<ConeBioassayDataEntry>>(response,object :
                TypeToken<ArrayList<ConeBioassayDataEntry>>(){}.getType())
        coneBioassayData.sortBy { it.formEntryRow }
        var metaData = ConeBioassayMetaData()
        metaData.serial = if (coneBioassayData[0].serial == null) {0} else {coneBioassayData[0].serial!!}
        metaData.sent = true
        var dataTable = ConeBioassayDataTable(metaData, coneBioassayData.size)
        dataTable.dataArray = coneBioassayData
        startActivity(gson.toJson(dataTable), ConeBioassay::class.java)
    }

    fun viewCDC(cdcString: String) {
        var cdcData = gson.fromJson<ArrayList<CdcHdtDataEntry>>(cdcString,object :
                TypeToken<ArrayList<CdcHdtDataEntry>>(){}.getType())
        cdcData.sortBy { it.formEntryRow }
        var metaData = CdcHdtMetaData()
        metaData.serial = if (cdcData[0].serial == null) {0} else {cdcData[0].serial!!}
        metaData.sent = true
        var dataTable = CdcHdtDataTable(metaData, cdcData.size)
        dataTable.dataArray = cdcData
        startActivity(gson.toJson(dataTable), CdcHdt::class.java)
    }

    fun startActivity(dataTableString : String, formActivityClass :  Class<*>) {
        this.alertDialog.dismiss()
        var intent = Intent(this, formActivityClass)
        var bundle = Bundle()
        bundle.putString("DataTableString",dataTableString)
        intent.putExtra("DataTableBundle", bundle)
        startActivity(intent)
    }

    fun viewIndoorRestingCollection (ircString: String) {
        var ircData = gson.fromJson<ArrayList<IndoorRestingCollectionDataEntry>>(ircString,object :
                TypeToken<ArrayList<IndoorRestingCollectionDataEntry>>(){}.getType())
        ircData.sortBy { it.formEntryRow }
        var metaData = IndoorRestingCollectionMetaData()
        metaData.serial = if (ircData[0].serial == null) {0} else {ircData[0].serial!!}
        metaData.sent = true
        var dataTable = IndoorRestingCollectionDataTable(metaData, ircData.size)
        dataTable.dataArray = ircData
        startActivity(gson.toJson(dataTable), IndoorRestingCollection::class.java)

    }

    fun viewHLC (hlcString: String) {
        var hlcData  = gson.fromJson<ArrayList<HLCDataEntry>>(hlcString,object :
                TypeToken<ArrayList<HLCDataEntry>>(){}.getType())
        hlcData.sortBy { it.formEntryRow }
        var metaData = HLCMetaData()
        metaData.serial = if (hlcData[0].serial == null) {0} else {hlcData[0].serial!!}
        metaData.sent = true
        var dataTable = HLCDataTable(metaData, hlcData.size)
        dataTable.dataArray = hlcData
        startActivity(gson.toJson(dataTable), HumanLandingCatch::class.java)
    }

    private fun missingDataForSerialAlert(serial: String) : AlertDialog
    {
        this.alertDialog.dismiss()
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage("No " + this.selectedForm + " Data Found for serial " + serial)
        return alertDialog
    }



}
