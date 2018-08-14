package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.gson.Gson
import android.util.Log
import java.io.OutputStreamWriter
import kotlin.properties.Delegates


class HumanLandingCatch : LanguagePreservingActivity() {

    var hLCMeta: HLCMetaData = HLCMetaData()
    var DataTableView: TableEntryView by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_landing_catch)
        var tvInOut : TextView
        var buttonNextOrFinish : Button
        tvInOut = findViewById(R.id.IndoorOutdoor) as TextView
        buttonNextOrFinish = findViewById(R.id.collectDataButton) as Button

        val bundle = intent.getBundleExtra("HLCMeta")
        this.hLCMeta  = bundle.getParcelable<HLCMetaData>("MetaBundle") as HLCMetaData
        if (this.hLCMeta.IN_OR_OUT == "in")
        {
            tvInOut.setText(getString(R.string.indoor).toUpperCase())
            buttonNextOrFinish.setText(getString(R.string.next))
        } else
        {
            tvInOut.setText(getString(R.string.outdoor).toUpperCase())
            buttonNextOrFinish.setText(getString(R.string.finish))
        }
        var dataTable = HLCDataTable(this.hLCMeta, 13, this)
        this.DataTableView = TableEntryView(this, dataTable)
        var dataView : LinearLayout = findViewById(R.id.TableContainer)
        dataView.addView(this.DataTableView)
        this.DataTableView.addView(this.DataTableView.createHeaderRow())
        val dataBundle = intent.getBundleExtra("HLCSaveData")
        if (dataBundle != null)
        {
            val data  = bundle.getSerializable("data") as ArrayList<HLCDataEntry>
            this.populateData(data)
        }

    }


    fun collectGoToNextPage(view: View) {
        try {
            var HLCDataArray = collectHLCData()
            this.writeData2Json(HLCDataArray)
        } catch(e : Exception) {
            return
        }



        if (this.hLCMeta.IN_OR_OUT == "in")
        {
            this.hLCMeta.IN_OR_OUT = "out"
            var intent = Intent(this, HumanLandingCatch::class.java)
            var bundle: Bundle = Bundle()
            bundle.putParcelable("MetaBundle", this.hLCMeta)
            intent.putExtra("HLCMeta", bundle)
            finish()
            startActivity(intent)
        } else {
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish()
            startActivity(intent)
        }
    }

    fun writeData2Json(data: ArrayList<HLCDataEntry>)
    {
        val gson = Gson()
        val jsonString: String = gson.toJson(data)
        val fsu = FileStoreUtil()
        val filename : String =
                fsu.CreateHLCFilename("UNSENT", this.hLCMeta.PROJECT_CODE,
                this.hLCMeta.DATE, this.hLCMeta.CLUSTER_NUMBER.toString(),
                this.hLCMeta.HOUSE_NUMBER.toString(), this.hLCMeta.IN_OR_OUT)
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
    fun collectHLCData() : ArrayList<HLCDataEntry>
    {
        val nRows = 13
        val nCols = 8
        var missingData = false
        var HLCDataArray = ArrayList<HLCDataEntry>()
        var table = findViewById<TableLayout>(R.id.mainHLCTable)
        for (iRow  : Int in 0..(nRows-1))
        {
            var thisHLC = HLCDataEntry(this.hLCMeta)
            var thisTableRow = table.getChildAt(iRow) as TableRow
            for(iCol : Int in 0..(nCols-1))
            {
                var thisTableCell = thisTableRow.getChildAt(iCol)
                var thisTableCellTag = thisTableCell.getTag().toString()
                if (thisTableCellTag == "HOUR") {
                    thisHLC.HOUR = (thisTableRow.getChildAt(iCol) as TextView).getText().toString()
                } else if (thisTableCellTag == "GAMBIAE") {
                    thisHLC.GAMBIAE = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.GAMBIAE == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else if (thisTableCellTag == "FUNESTUS") {
                    thisHLC.FUNESTUS = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.FUNESTUS == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else if (thisTableCellTag == "COUSTANI") {
                    thisHLC.COUSTANI = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.COUSTANI == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else if (thisTableCellTag == "MANSONIA") {
                    thisHLC.MANSONIA = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.MANSONIA == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else if (thisTableCellTag == "AEDES") {
                    thisHLC.AEDES = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.AEDES == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else if (thisTableCellTag == "COQUILETTIDIA") {
                    thisHLC.COQUILETTIDIA = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.COQUILETTIDIA == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else if (thisTableCellTag == "OTHER") {
                    thisHLC.OTHER = (thisTableRow.getChildAt(iCol) as EditText).getText().toString().toIntOrNull()
                    if (thisHLC.OTHER == null)
                    {
                        (thisTableRow.getChildAt(iCol) as EditText).setError(getString(R.string.missing_data))
                        missingData = true
                    }
                } else {
                    throw error("Incorrect Tag supplied")
                }
            }
            HLCDataArray.add(thisHLC)
        }
        if (missingData)
        {
            throw(error("Missing Data"))
        }

        return HLCDataArray
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
