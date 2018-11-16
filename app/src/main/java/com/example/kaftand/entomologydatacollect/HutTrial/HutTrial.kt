package com.example.kaftand.entomologydatacollect.HutTrial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HLCDataTable
import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.MainActivity
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.TableEntryView
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.google.gson.Gson
import org.w3c.dom.Text
import java.io.OutputStreamWriter
import kotlin.properties.Delegates

class HutTrial : LanguagePreservingActivity() {

    var metaData: HutTrialMetaData by Delegates.notNull()
    var DataTableView: TableEntryView<HutTrialDataEntry> by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_hut_trial)
        val gson = Gson()
        val metaDataBundle = intent.getBundleExtra("MetaData")
        val tableBundle = intent.getBundleExtra("DataTableBundle")

        val dataTable = if (metaDataBundle != null) {
            HutTrialDataTable(metaDataBundle.getParcelable("MetaData") as HutTrialMetaData,
                    (metaDataBundle.getParcelable("MetaData") as HutTrialMetaData).N_HUTS*3)
        } else {
            gson.fromJson(tableBundle.getString("DataTableString"), HutTrialDataTable::class.java)
        }
        this.metaData = dataTable.metaData
        this.DataTableView = TableEntryView<HutTrialDataEntry>(this, dataTable)
        this.customHeaderTableFormat()
        val dataTableContainer = findViewById<LinearLayout>(R.id.MainTableContainer)
        dataTableContainer.addView(this.DataTableView,0)
    }

    fun completeForm(view: View) {
        this.DataTableView.tableData.metaData.completed = true
        this.DataTableView.tableData.metaData.sent = false
        writeData2Json(this.DataTableView.tableData as HutTrialDataTable)
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish()
        startActivity(intent)
    }

    fun saveFormForTomorrow(view: View) {
        this.DataTableView.tableData.metaData.completed = false
        this.DataTableView.tableData.metaData.sent = false
        writeData2Json(this.DataTableView.tableData as HutTrialDataTable)
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish()
        startActivity(intent)
    }

    fun customHeaderTableFormat()  {
        val verticalHeader1 = findViewById<TableRow>(R.id.VerticalHeader1)
        val verticalHeader2 = findViewById<TableRow>(R.id.VerticalHeader2)
        val colWidthUnscaled = this.DataTableView.getColWidth()[0]
        val colWidthScaled = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, colWidthUnscaled, this.resources.displayMetrics))

        for (iCol in 0 until verticalHeader1.childCount) {
            var thisHeaderCell = verticalHeader1.getChildAt(iCol)
            thisHeaderCell.layoutParams.width = colWidthScaled
        }
        for (iCol in 0 until verticalHeader2.childCount) {
            var thisHeaderCell = verticalHeader2.getChildAt(iCol)
            thisHeaderCell.layoutParams.width = colWidthScaled
        }
    }

    fun writeData2Json(data: HutTrialDataTable)
    {
        val gson = Gson()
        val jsonString: String = gson.toJson(data)
        val fsu = FileStoreUtil()
        val tsLong = System.currentTimeMillis()
        val ts = tsLong.toString()
        val filename = this.metaData.getFilename()
        writeToFile(jsonString, filename, getApplicationContext())
        println(this.getFilesDir().getAbsolutePath().toString())
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

    fun setSpan(view: TextView, span: Int) {
        val lp = TableRow.LayoutParams()
        lp.span = span
        lp.height = TableRow.LayoutParams.WRAP_CONTENT
        lp.width = TableRow.LayoutParams.WRAP_CONTENT
        lp.weight = 1f
        view.layoutParams = lp
    }
}