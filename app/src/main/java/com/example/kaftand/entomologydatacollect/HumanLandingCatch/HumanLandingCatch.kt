package com.example.kaftand.entomologydatacollect.HumanLandingCatch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.gson.Gson
import android.util.Log
import com.example.kaftand.entomologydatacollect.*
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.formCountTracker
import java.io.OutputStreamWriter
import kotlin.properties.Delegates


class HumanLandingCatch : LanguagePreservingActivity() {

    var hLCMeta: HLCMetaData = HLCMetaData()
    var DataTableView: TableEntryView<HLCDataEntry> by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_landing_catch)
        var buttonNextOrFinish : Button
        buttonNextOrFinish = findViewById(R.id.collectDataButton) as Button

        val metaDataBundle = intent.getBundleExtra("HLCMeta")
        val dataTableBundle = intent.getBundleExtra("DataTableBundle")
        val nRows = 13*2
        var dataTable = HLCDataTable(this.hLCMeta, nRows)

        if(dataTableBundle != null) {
            val gson = Gson()
            var dataTableString = dataTableBundle.getString("DataTableString")
            dataTable = gson.fromJson<HLCDataTable>(dataTableString, HLCDataTable::class.java!!)
            this.hLCMeta = dataTable.metaData
        } else if (metaDataBundle != null) {
            this.hLCMeta  = metaDataBundle.getParcelable<HLCMetaData>("MetaBundle") as HLCMetaData
            dataTable = HLCDataTable(this.hLCMeta, nRows)
        } else {
            error("No bundle passed in")
        }

        this.DataTableView = TableEntryView<HLCDataEntry>(this, dataTable)
        var lp = TableLayout.LayoutParams()
        lp.width = TableLayout.LayoutParams.MATCH_PARENT
        lp.height = TableLayout.LayoutParams.WRAP_CONTENT
        this.DataTableView.layoutParams = lp
        var dataView : LinearLayout = findViewById(R.id.MainTableContainer)
        var headerTableContainer : LinearLayout = findViewById(R.id.TableHeaderContainer)
        dataView.addView(this.DataTableView,0)
        headerTableContainer.addView(this.DataTableView.createHeaderTable(), 0)

    }


    fun collectGoToNextPage(view: View) {
        var dataTable = this.DataTableView.tableData as HLCDataTable
        if (this.DataTableView.ensureNoNull(this.DataTableView)) {
            this.writeData2Json(dataTable)
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

        returnIntent.putExtra("result", gson.toJson(this.DataTableView.tableData as HLCDataTable))
        returnIntent.putExtra("metaData", this.hLCMeta)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    fun writeData2Json(data: HLCDataTable)
    {
        if (this.hLCMeta.count == formCountTracker.readFormCount(this.hLCMeta.formType, this)) {
            formCountTracker.increaseFormCount(this.hLCMeta.formType, this)
        }
        val gson = Gson()
        val jsonString: String = gson.toJson(data)
        val fsu = FileStoreUtil()
        val filename = this.hLCMeta.getFilename()
        writeToFile(jsonString, filename, getApplicationContext())
        println(this.getFilesDir().getAbsolutePath().toString())
    }

    fun writeToFile(data: String, filename: String, context: Context) {
        try {
            var gson = Gson()
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: Exception) {
            Log.e("Exception", "File write failed: " + e.toString())
        }

    }

    fun populateData(HLCDataArray : ArrayList<HLCDataEntry>) {
        for (iEntry : Int in 0..HLCDataArray.size)
        {
            var HLCRow = HLCDataArray.get(iEntry);
            var thisRowHour = "H_" + HLCRow.HOUR?.replace("-","_")?.replace(":","_");
            val gambiaeID = resources.getIdentifier(thisRowHour + "_GAMBIAE", "id", packageName)
            findViewById<EditText>(gambiaeID).setText(HLCRow.GAMBIAE.toString())
            val funestusID = resources.getIdentifier(thisRowHour + "_FUNESTUS", "id", packageName)
            findViewById<EditText>(funestusID).setText(HLCRow.FUNESTUS.toString())
            val coustaniID = resources.getIdentifier(thisRowHour + "_COUSTANI", "id", packageName)
            findViewById<EditText>(coustaniID).setText(HLCRow.COUSTANI.toString())
            val mansoniaID = resources.getIdentifier(thisRowHour + "_MANSONIA", "id", packageName)
            findViewById<EditText>(mansoniaID).setText(HLCRow.MANSONIA.toString())
            val aedesID = resources.getIdentifier(thisRowHour + "_AEDES", "id", packageName)
            findViewById<EditText>(aedesID).setText(HLCRow.AEDES.toString())
            val coqID = resources.getIdentifier(thisRowHour + "_COQUILETTIDIA", "id", packageName)
            findViewById<EditText>(coqID).setText(HLCRow.COQUILETTIDIA.toString())
            val otherID = resources.getIdentifier(thisRowHour + "_OTHER", "id", packageName)
            findViewById<EditText>(otherID).setText(HLCRow.OTHER.toString())


        }
    }

}
