package com.example.kaftand.entomologydatacollect.Phase1
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.MainActivity
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.TableEntryView
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.formCountTracker
import com.google.gson.Gson
import java.io.OutputStreamWriter
import kotlin.properties.Delegates
class Phase1 : LanguagePreservingActivity() {
    var metaData: Phase1MetaData by Delegates.notNull()
    var DataTableView: TableEntryView<Phase1DataEntry> by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_phase1)
        val gson = Gson()
        val metaDataBundle = intent.getBundleExtra("MetaData")
        val tableBundle = intent.getBundleExtra("DataTableBundle")

        val dataTable = if (metaDataBundle != null) {
            Phase1DataTable(metaDataBundle.getParcelable("MetaData") as Phase1MetaData,
                    4)
        } else {
            gson.fromJson(tableBundle.getString("DataTableString"), Phase1DataTable::class.java)
        }
        this.metaData = dataTable.metaData
        this.DataTableView = TableEntryView<Phase1DataEntry>(this, dataTable)
        this.customHeaderTableFormat()
        val dataTableContainer = findViewById<LinearLayout>(R.id.MainTableContainer)
        dataTableContainer.addView(this.DataTableView, 0)

        var saveButton = findViewById<Button>(R.id.collectDataButton)
        var completeButton = findViewById<Button>(R.id.completeButton)
        if (this.metaData.sent) {
            saveButton.visibility = View.GONE
            completeButton.visibility = View.GONE
        }

        var serialNumberLabel = findViewById<TextView>(R.id.serial_label)
        serialNumberLabel.text = "serial: " + this.metaData.serial.toString()
    }

    fun completeForm(view: View) {

        if (this.DataTableView.ensureNoNull(this.DataTableView)) {
            this.DataTableView.tableData.metaData.completed = true
            this.DataTableView.tableData.metaData.sent = false
            writeData2Json(this.DataTableView.tableData as Phase1DataTable)
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish()
            startActivity(intent)
        } else {
            this.DataTableView.alertMissingData()
        }
    }

    override fun onBackPressed() {
        val gson = Gson()
        var returnIntent = Intent()

        returnIntent.putExtra("result", gson.toJson(this.DataTableView.tableData as Phase1DataTable))
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    fun saveFormForTomorrow(view: View) {
        this.DataTableView.tableData.metaData.completed = false
        this.DataTableView.tableData.metaData.sent = false
        writeData2Json(this.DataTableView.tableData as Phase1DataTable)
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish()
        startActivity(intent)
    }

    fun customHeaderTableFormat() {
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

    fun writeData2Json(data: Phase1DataTable) {
        if (this.metaData.count == formCountTracker.readFormCount(this.metaData.formType, this)) {
            formCountTracker.increaseFormCount(this.metaData.formType, this)
        }
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
}