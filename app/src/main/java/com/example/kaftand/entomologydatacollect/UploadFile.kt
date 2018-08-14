package com.example.kaftand.entomologydatacollect

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.R.attr.path
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.*
import com.android.volley.toolbox.Volley
import java.io.File
import java.sql.Date
import java.text.SimpleDateFormat
import org.json.JSONObject
import java.io.FileInputStream
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.Response.success
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.StringRequest
import java.io.UnsupportedEncodingException
import com.android.volley.VolleyLog
import com.android.volley.AuthFailureError
import kotlin.math.roundToInt


open class UploadFile : LanguagePreservingActivity() {

    var unsentFilesWithMeta: ArrayList<SavedFileInfo> = ArrayList()
    var sentFilesWithMeta: ArrayList<SavedFileInfo> = ArrayList()
    val IP_PORT: String = "https://ihientodatacollection.appspot.com"//"http://192.168.9.87:8080"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file)
        this.setFileList()
        this.addTableRows()

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
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        val cellWidth = TableLayout.LayoutParams.WRAP_CONTENT //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20F, getResources().getDisplayMetrics()).roundToInt();
        val dateCellWidth = TableLayout.LayoutParams.WRAP_CONTENT//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20F, getResources().getDisplayMetrics()).roundToInt();
        val cellHeight = TableLayout.LayoutParams.WRAP_CONTENT
        for (iUnsentFile in unsentFilesWithMeta) {

            val row = TableRow(this)
            val lp = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            row.setLayoutParams(lp)
            row.setOnClickListener{
                this.fileClicked()
            }
            var sentView = ImageView(this)
            sentView.setImageResource(R.drawable.ic_close_black_24dp);
            var formTypeView = TextView(this)
            this.formatCell(formTypeView, lp, cellHeight, cellWidth)
            formTypeView.setText(iUnsentFile.formType)
            var projectCodeView = TextView(this)
            this.formatCell(projectCodeView, lp, cellHeight, cellWidth)
            projectCodeView.setText(iUnsentFile.studyCode)
            var dateCodeView = TextView(this)
            this.formatCell(dateCodeView, lp, cellHeight, dateCellWidth)
            dateCodeView.setText(formatter.format(Date(iUnsentFile.file.lastModified())))

            row.addView(sentView)
            row.addView(formTypeView)
            row.addView(projectCodeView)
            row.addView(dateCodeView)
            ll.addView(row)
        }

        for (iSentFile in sentFilesWithMeta) {

            val row = TableRow(this)
            val lp = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            row.setLayoutParams(lp)

            row.setOnClickListener{
                this.fileClicked()
            }
            var sentView = ImageView(this)
            sentView.setImageResource(R.drawable.ic_check_black_24dp);
            var formTypeView = TextView(this)
            formTypeView.setText(iSentFile.formType)
            this.formatCell(formTypeView, lp, cellHeight,cellWidth)
            var projectCodeView = TextView(this)
            projectCodeView.setText(iSentFile.studyCode)
            this.formatCell(projectCodeView, lp, cellHeight, cellWidth)
            var dateCodeView = TextView(this)
            this.formatCell(dateCodeView, lp, cellHeight, dateCellWidth)
            dateCodeView.setText(formatter.format(Date(iSentFile.file.lastModified())))

            row.addView(sentView)
            row.addView(formTypeView)
            row.addView(projectCodeView)
            row.addView(dateCodeView)
            ll.addView(row)
        }

    }

    fun formatCell(view: View, rowParams: TableRow.LayoutParams, height: Int, width: Int)
    {
        rowParams.width = width
        rowParams.height = height
        rowParams.leftMargin = 10
        view.layoutParams = rowParams
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

    fun fileClicked()
    {
        Log.i("file clicked","CLICK")
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


}
