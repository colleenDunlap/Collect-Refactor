package com.example.kaftand.entomologydatacollect

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import java.io.UnsupportedEncodingException
import com.android.volley.VolleyLog
import com.android.volley.AuthFailureError
import com.example.kaftand.entomologydatacollect.CdcHdt.CdcHdtDataTable
import com.example.kaftand.entomologydatacollect.ConeBioassay.ConeBioassayDataTable
import com.example.kaftand.entomologydatacollect.FormInterfaces.TabularData
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HLCDataTable
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrialDataTable
import com.example.kaftand.entomologydatacollect.IndoorRestingCollection.IndoorRestingCollectionDataTable
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import com.example.kaftand.entomologydatacollect.Util.SavedFileInfo
import com.google.gson.Gson
import java.io.OutputStreamWriter
import kotlin.properties.Delegates


open class UploadFile : LanguagePreservingActivity() {

    var unsentFilesWithMeta: ArrayList<SavedFileInfo> = ArrayList()
    var sentFilesWithMeta: ArrayList<SavedFileInfo> = ArrayList()
    var uploadableFiles: ArrayList<SavedFileInfo> = ArrayList()
    var formTypeString: String by Delegates.notNull()
    var formTypeClass: Class<*> by Delegates.notNull()
    var uploadedForms: Int by Delegates.notNull()
    var processDialog: AlertDialog by Delegates.notNull()
    val IP_PORT: String = "https://ihientodatacollection.appspot.com"//"http://192.168.9.87:8080"ngrok
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file)
        this.uploadedForms = 0
        val bundle = intent.getBundleExtra("FormType")
        this.formTypeString  = bundle.getString("formString")
        this.formTypeClass = this.getTypeClass(this.formTypeString)
        this.setFileList()
        this.findSendableFiles()
        this.addTableRows()
        this.processDialog = progressDialog()

    }

    fun getTypeClass(formTypeString: String) : Class<*>{
        if (formTypeString == FormTypeKeys.HLC)
        {
            return HLCDataTable::class.java
        } else if (formTypeString == FormTypeKeys.HutTrial) {
            return  HutTrialDataTable::class.java
        } else if (formTypeString == FormTypeKeys.CdcHdt) {
            return CdcHdtDataTable::class.java
        } else if (formTypeString == FormTypeKeys.IndoorRestingCollection) {
            return IndoorRestingCollectionDataTable::class.java
        }  else if (formTypeString == FormTypeKeys.ConeBioassay) {
            return ConeBioassayDataTable::class.java
        }
        return  HLCDataTable::class.java
    }


    fun setFileList () {
        val dir = this.filesDir
        val files = dir.listFiles()


        for (inFile in files) {
            if (inFile.isDirectory()) {
                // is directory
            } else {
                var thisFile = SavedFileInfo()
                thisFile.setMetaDataFromFileName(inFile)
                if (thisFile.sent == "SENT") {
                    this.sentFilesWithMeta.add(thisFile)
                } else if (thisFile.sent == "UNSENT") {
                    this.unsentFilesWithMeta.add(thisFile)
                }
            }
        }

        this.sentFilesWithMeta.sortBy({ it.file.lastModified() })
        this.unsentFilesWithMeta.sortBy({ it.file.lastModified() })
    }

    fun findSendableFiles() {
        val gson = Gson()
        for (iUnsentFile in unsentFilesWithMeta) {
            var thisDataTable = gson.fromJson(iUnsentFile.file.readText(), this.formTypeClass) as TabularData <Any>
            if((thisDataTable.metaData.formType == this.formTypeString) and (thisDataTable.metaData.completed)){
                this.uploadableFiles.add(iUnsentFile)
            }
        }
    }

    fun addTableRows()
    {
        val ll = findViewById(R.id.FileNameTable) as TableLayout
        val gson = Gson()
        var iFile = 0
        var firstRow = true
        for (iUnsentFile in unsentFilesWithMeta) {
            var thisDataTable = gson.fromJson(iUnsentFile.file.readText(), this.formTypeClass) as TabularData <Any>
            if (firstRow) {
                firstRow = false
                ll.addView(formatRow(iFile, thisDataTable.buildInfoHeader(this), true))
            }
            val completeResource = if(thisDataTable.metaData.completed) {R.drawable.ic_check_black_24dp}
                else {R.drawable.ic_close_black_24dp}

            val row = thisDataTable.buildInfoRow(this, R.drawable.ic_close_black_24dp, completeResource)
            row.setOnClickListener{
                this.fileClicked(iUnsentFile)
            }
            if(thisDataTable.metaData.formType == this.formTypeString) {
                ll.addView(formatRow(iFile, row, true))
                iFile += 1
            }
        }

        for (iSentFile in sentFilesWithMeta) {

            var thisDataTable = gson.fromJson(iSentFile.file.readText(), this.formTypeClass) as TabularData <Any>
            if (firstRow) {
                firstRow = false
                ll.addView(formatRow(iFile, thisDataTable.buildInfoHeader(this), true))
            }
            val completeResource = if(thisDataTable.metaData.completed) {R.drawable.ic_check_black_24dp}
                else {R.drawable.ic_close_black_24dp}
            val row = thisDataTable.buildInfoRow(this, R.drawable.ic_check_black_24dp, completeResource)
            row.setOnClickListener{
                this.fileClicked(iSentFile)
            }

            if (thisDataTable.metaData.formType == this.formTypeString) {
                ll.addView(formatRow(iFile, row, true))
                iFile += 1
            }
        }
    }

    fun formatTableRows(ll : LinearLayout) {
        for (iRow in 0 until ll.childCount) {
            val thisRow = ll.getChildAt(iRow) as TableRow
            this.formatRow(iRow, thisRow, false)
        }
    }



    fun uploadForms(view: View)
    {
        var iUploaded = 0
        startProcess()
        for (iUnsentFilesWithMeta in uploadableFiles)
        {
            iUploaded
            uploadEachForm(iUnsentFilesWithMeta)
        }
    }

    open fun fileClicked(file : SavedFileInfo)
    {
    }

    fun uploadEachForm(iUnsentFilesWithMeta: SavedFileInfo)
    {
        val context = this
        val gson = Gson()
        var thisDataTable = gson.fromJson(iUnsentFilesWithMeta.file.readText(), this.formTypeClass) as TabularData <Any>
        val requestBody = iUnsentFilesWithMeta.file.readText()
        val requestQueue = Volley.newRequestQueue(this)
        val uRL = "$IP_PORT/${this.formTypeString}"


        val stringRequest = object : StringRequest(Request.Method.POST, uRL, object : Response.Listener<String> {
            override fun onResponse(response: String) {
                Log.i("VOLLEY", response)
                thisDataTable.metaData.sent = true
                val dataString = gson.toJson(thisDataTable)
                writeToFile(dataString, thisDataTable.metaData.getFilename(), context)
                iUnsentFilesWithMeta.reNameFileAfterSent()
                increaseUploadedForms()
                if (checkIfFinishedUploading()) {
                    endProcess()
                    finish();
                    startActivity(getIntent());
                }
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.e("VOLLEY", error.toString())
                endProcess()
                alertNoInternet()
            }
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                try {
                    return requestBody?.toByteArray(Charsets.UTF_8)
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8")
                    return null
                }

            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                var responseString = ""
                if (response != null) {
                    responseString = response.statusCode.toString()
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response))
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue.add(stringRequest);
    }


    fun formatRow(iRow: Int, row: TableRow, isHeader : Boolean) : TableRow {
        var border : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
        var colWidths = getColWidth(row)

        if (iRow % 2 == 0) {
            border = ResourcesCompat.getDrawable(resources, R.drawable.border2, null)
        }
        var minTextSize = Float.POSITIVE_INFINITY
        for (iChild in 0 until row.childCount) {
            if (row.getChildAt(iChild) is TextView) {
                var cell = row.getChildAt(iChild) as TextView
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cell.background = border
                } else {
                    cell.setBackgroundDrawable(border)
                }
                var cellLp = cell.layoutParams
                cellLp.height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics));
                var thisWidth = colWidths[iChild]
                cellLp.width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, thisWidth, resources.displayMetrics));
                cell.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
                cell.setSelectAllOnFocus(true)
                cell.gravity = Gravity.CENTER
                minTextSize = Math.min(getTextSize(cell, thisWidth), minTextSize)
            }
        }

        for (iChild in 0 until row.childCount) {
            if (row.getChildAt(iChild) is TextView) {
                var cell = row.getChildAt(iChild) as TextView
                if (isHeader) {
                    cell.setTextSize(TypedValue.COMPLEX_UNIT_SP, minTextSize)
                }
            }
        }

        return row
    }

    private fun alertNoInternet()
    {
        val alertDialog = AlertDialog.Builder(this@UploadFile).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(getString(R.string.no_internet))
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }

    private fun progressDialog() : AlertDialog
    {
        val alertDialog = AlertDialog.Builder(this@UploadFile).create()
        val pb = ProgressBar(this)
        pb.isIndeterminate = true
        pb.visibility = View.VISIBLE
        alertDialog.addContentView(pb, ViewGroup.LayoutParams(40,40))
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage("Uploading")
        return alertDialog
    }

    private fun startProcess() {
        this.processDialog.show()
    }

    private fun endProcess() {
        this.processDialog.hide()
    }

    fun checkIfFinishedUploading() : Boolean {
        return (this.uploadableFiles.size == this.uploadedForms)
    }

    fun increaseUploadedForms() {
        this.uploadedForms += 1
    }

    fun getColWidth(row : TableRow) : MutableList<Float> {
        var size = row.childCount
        val displayMetrics = DisplayMetrics()
        val wm : WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.getDefaultDisplay().getMetrics(displayMetrics)
        val measuredWidth = displayMetrics.widthPixels * 0.9
        var colWidths = mutableListOf<Float>();
        for(iCol in 0 until size)
        {
            colWidths.add(((measuredWidth)/(size.toDouble())).toFloat())
        }
        return colWidths
    }

    fun getTextSize(cell : TextView, cellSize : Float) : Float {
        val originalTextSize = cell.textSize
        val bounds = Rect()
        cell.paint.getTextBounds(cell.text.toString(), 0, cell.text.toString().length, bounds);
        val width = bounds.width().toFloat() * getResources().getDisplayMetrics().scaledDensity
        return (0.8*(cellSize*originalTextSize)/width).toFloat()
    }

    fun writeToFile(data: String, filename: String, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: Exception) {
            Log.e("Exception", "File write failed: " + e.toString())
        }

    }

}
