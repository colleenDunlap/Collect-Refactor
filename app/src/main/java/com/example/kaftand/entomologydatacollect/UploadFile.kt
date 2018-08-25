package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
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
import com.example.kaftand.entomologydatacollect.FormInterfaces.TabularData
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HLCDataTable
import com.example.kaftand.entomologydatacollect.Util.SavedFileInfo
import com.google.gson.Gson
import kotlin.properties.Delegates


open class UploadFile : LanguagePreservingActivity() {

    var unsentFilesWithMeta: ArrayList<SavedFileInfo> = ArrayList()
    var sentFilesWithMeta: ArrayList<SavedFileInfo> = ArrayList()
    var formTypeString: String by Delegates.notNull()
    var formTypeClass: Class<*> by Delegates.notNull()
    val IP_PORT: String = "https://85523dcb.ngrok.io"//"http://192.168.9.87:8080"//"https://ihientodatacollection.appspot.com"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file)
        val bundle = intent.getBundleExtra("FormType")
        this.formTypeString  = bundle.getString("formString")
        this.formTypeClass = this.getTypeClass(this.formTypeString)
        this.setFileList()
        this.addTableRows()

    }

    fun getTypeClass(formTypeString: String) : Class<*>{
        if (formTypeString == getString(R.string.human_landing_catch))
        {
            return HLCDataTable::class.java
        } else if (formTypeString == getString(R.string.hut_study)) {
            return  HLCDataTable::class.java
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

    fun addTableRows()
    {
        val ll = findViewById(R.id.FileNameTable) as TableLayout
        val gson = Gson()
        var iFile = 0
        for (iUnsentFile in unsentFilesWithMeta) {
            var thisDataTable = gson.fromJson(iUnsentFile.file.readText(), this.formTypeClass) as TabularData <Any>
            if (iFile == 0) {
                ll.addView(formatRow(iFile, thisDataTable.buildInfoHeader(this), true))
            }
            val row = thisDataTable.buildInfoRow(this, R.drawable.ic_close_black_24dp)
            row.setOnClickListener{
                this.fileClicked(iUnsentFile)
            }
            ll.addView(formatRow(iFile, row, true))
            iFile += 1
        }

        for (iSentFile in sentFilesWithMeta) {

            var thisDataTable = gson.fromJson(iSentFile.file.readText(), this.formTypeClass) as TabularData <Any>
            val row = thisDataTable.buildInfoRow(this, R.drawable.ic_check_black_24dp)
            row.setOnClickListener{
                this.fileClicked(iSentFile)
            }

            ll.addView(formatRow(iFile, row, true))
            iFile += 1
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
        for (iUnsentFilesWithMeta in unsentFilesWithMeta)
        {
            iUploaded
            uploadEachForm(iUnsentFilesWithMeta)
        }
    }

    open fun fileClicked(file : SavedFileInfo)
    {
        Log.i("file clicked",file.studyCode)
    }

    fun uploadEachForm(iUnsentFilesWithMeta: SavedFileInfo)
    {
        val requestBody = iUnsentFilesWithMeta.file.readText()
        val requestQueue = Volley.newRequestQueue(this)
        val uRL = "$IP_PORT/HLC"
        val stringRequest = object : StringRequest(Request.Method.POST, uRL, object : Response.Listener<String> {
            override fun onResponse(response: String) {
                Log.i("VOLLEY", response)
                iUnsentFilesWithMeta.reNameFileAfterSent()
                finish();
                startActivity(getIntent());
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.e("VOLLEY", error.toString())
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
        requestQueue.add(stringRequest);
    }


    fun formatRow(iRow: Int, row: TableRow, isHeader : Boolean) : TableRow {
        var border : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
        var colWidths = getColWidth(row)

        if (iRow % 2 == 0) {
            border = ResourcesCompat.getDrawable(resources, R.drawable.border2, null)
        }
        var minTextSize = Float.POSITIVE_INFINITY
        for (iChild in 1 until row.childCount) {
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

        for (iChild in 1 until row.childCount) {
            var cell = row.getChildAt(iChild) as TextView
            if (isHeader) {
                cell.setTextSize(TypedValue.COMPLEX_UNIT_SP, minTextSize)
            }
        }

        return row
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



}
