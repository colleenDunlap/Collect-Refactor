package com.example.kaftand.entomologydatacollect.ConeBioassay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import com.example.kaftand.entomologydatacollect.LanguagePreservingActivity
import com.example.kaftand.entomologydatacollect.MainActivity
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.TableEntryView
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.formCountTracker
import com.google.gson.Gson
import java.io.OutputStreamWriter
import kotlin.properties.Delegates
import kotlin.reflect.KMutableProperty0


class ConeBioassay : LanguagePreservingActivity() {

    var metaData: ConeBioassayMetaData by Delegates.notNull()
    var DataTableView: TableEntryView<ConeBioassayDataEntry> by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_cone_bioassay)
        val gson = Gson()
        val metaDataBundle = intent.getBundleExtra("MetaData")
        val tableBundle = intent.getBundleExtra("DataTableBundle")

        val dataTable = if (metaDataBundle != null) {
            ConeBioassayDataTable(metaDataBundle.getParcelable("MetaData") as ConeBioassayMetaData,
                    5)
        } else {
            gson.fromJson(tableBundle.getString("DataTableString"), ConeBioassayDataTable::class.java)
        }
        this.metaData = dataTable.metaData
        this.DataTableView = TableEntryView<ConeBioassayDataEntry>(this, dataTable)
        this.customHeaderTableFormat()
        this.fillPsuedoTables()
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

    fun fillPsuedoTables() {

        val psuedoData = this.DataTableView.tableData.dataArray[1]
        val sectionATable = findViewById<TableLayout>(R.id.section_a_table)
        val sectionBTable = findViewById<TableLayout>(R.id.section_b_table)

        val A_EXPOSURE_ROW = 0
        val A_KD_ROW = 1
        val A_24_ROW = 2
        val A_48_ROW = 3
        val A_72_ROW = 4
        val A_96_ROW = 5
        val A_120_ROW = 6

        val A_PERF_COL = 1
        val A_SCORED_COL = 2
        val A_ENTER_COL = 3

        val exposurePerfEdit = ((sectionATable.getChildAt(A_EXPOSURE_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        exposurePerfEdit.setText(this.formatText(psuedoData.EXPOSURE_PERFORMED_BY))

        val exposureScoreEdit = ((sectionATable.getChildAt(A_EXPOSURE_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        exposureScoreEdit.setText(this.formatText(psuedoData.EXPOSURE_SCORED_BY))
        val exposureEnterEdit = ((sectionATable.getChildAt(A_EXPOSURE_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        exposureEnterEdit.setText(this.formatText(psuedoData.EXPOSURE_DATA_ENTERED_BY))
        val kd60PerfEdit = ((sectionATable.getChildAt(A_KD_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        kd60PerfEdit.setText(this.formatText(psuedoData.KD60_PERFORMED_BY))
        val kd60ScoredEdit = ((sectionATable.getChildAt(A_KD_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        kd60ScoredEdit.setText(this.formatText(psuedoData.KD60_SCORED_BY))
        val kd60EnteredEdit = ((sectionATable.getChildAt(A_KD_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        kd60EnteredEdit.setText(this.formatText(psuedoData.KD60_DATA_ENTERED_BY))
        val m24PerfEdit = ((sectionATable.getChildAt(A_24_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        m24PerfEdit.setText(this.formatText(psuedoData.M24_PERFORMED_BY))
        val m24ScoredEdit = ((sectionATable.getChildAt(A_24_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        m24ScoredEdit.setText(this.formatText(psuedoData.M24_SCORED_BY))
        val m24EnteredEdit = ((sectionATable.getChildAt(A_24_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        m24EnteredEdit.setText(this.formatText(psuedoData.M24_DATA_ENTERED_BY))

        val m48PerfEdit = ((sectionATable.getChildAt(A_48_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        m48PerfEdit.setText(this.formatText(psuedoData.M48_PERFORMED_BY))
        val m48ScoredEdit = ((sectionATable.getChildAt(A_48_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        m48ScoredEdit.setText(this.formatText(psuedoData.M48_SCORED_BY))
        val m48EnteredEdit = ((sectionATable.getChildAt(A_48_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        m48EnteredEdit.setText(this.formatText(psuedoData.M48_DATA_ENTERED_BY))

        val m72PerfEdit = ((sectionATable.getChildAt(A_72_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        m72PerfEdit.setText(this.formatText(psuedoData.M72_PERFORMED_BY))
        val m72ScoredEdit = ((sectionATable.getChildAt(A_72_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        m72ScoredEdit.setText(this.formatText(psuedoData.M72_SCORED_BY))
        val m72EnteredEdit = ((sectionATable.getChildAt(A_72_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        m72EnteredEdit.setText(this.formatText(psuedoData.M72_DATA_ENTERED_BY))

        val m96PerfEdit = ((sectionATable.getChildAt(A_96_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        m96PerfEdit.setText(this.formatText(psuedoData.M96_PERFORMED_BY))
        val m96ScoredEdit = ((sectionATable.getChildAt(A_96_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        m96ScoredEdit.setText(this.formatText(psuedoData.M96_SCORED_BY))
        val m96EnteredEdit = ((sectionATable.getChildAt(A_96_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        m96EnteredEdit.setText(this.formatText(psuedoData.M96_DATA_ENTERED_BY))

        val m120PerfEdit = ((sectionATable.getChildAt(A_120_ROW) as TableRow).getChildAt(A_PERF_COL) as EditText)
        m120PerfEdit.setText(this.formatText(psuedoData.M120_PERFORMED_BY))
        val m120ScoredEdit = ((sectionATable.getChildAt(A_120_ROW) as TableRow).getChildAt(A_SCORED_COL) as EditText)
        m120ScoredEdit.setText(this.formatText(psuedoData.M120_SCORED_BY))
        val m120EnteredEdit = ((sectionATable.getChildAt(A_120_ROW) as TableRow).getChildAt(A_ENTER_COL) as EditText)
        m120EnteredEdit.setText(this.formatText(psuedoData.M120_DATA_ENTERED_BY))



        val B_ACCLIM_ROW = 0
        val B_EXPOSURE_ROW = 1
        val B_KD_ROW = 2
        val B_24_ROW = 3
        val B_48_ROW = 4
        val B_72_ROW = 5
        val B_96_ROW = 6
        val B_120_ROW = 7

        val B_START_COL = 1
        val B_END_COL = 2
        val B_TEMP_COL = 3
        val B_HUM_COL = 4

        val acclimStartEdit = ((sectionBTable.getChildAt(B_ACCLIM_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        acclimStartEdit.setText(this.formatText(psuedoData.ACCLIMATIZATION_START_TIME))
        val acclimEndTime = ((sectionBTable.getChildAt(B_ACCLIM_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        acclimEndTime.setText(this.formatText(psuedoData.ACCLIMATIZATION_END_TIME))
        val acclimTempEdit = ((sectionBTable.getChildAt(B_ACCLIM_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        acclimTempEdit.setText(this.formatText(psuedoData.ACCLIMATIZATION_TEMP.toString()))
        val acclimHumEdit = ((sectionBTable.getChildAt(B_ACCLIM_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        acclimHumEdit.setText(this.formatText(psuedoData.ACCLIMATIZATION_HUMIDITY.toString()))

        val exposureStartEdit = ((sectionBTable.getChildAt(B_EXPOSURE_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        exposureStartEdit.setText(this.formatText(psuedoData.EXPOSURE_START_TIME))
        val exposureEndEdit = ((sectionBTable.getChildAt(B_EXPOSURE_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        exposureEndEdit.setText(this.formatText(psuedoData.EXPOSURE_END_TIME))
        val exposureTempEdit = ((sectionBTable.getChildAt(B_EXPOSURE_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        exposureTempEdit.setText(this.formatText(psuedoData.EXPOSURE_TEMP.toString()))
        val exposureHumEdit = ((sectionBTable.getChildAt(B_EXPOSURE_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        exposureHumEdit.setText(this.formatText(psuedoData.EXPOSURE_HUMIDITY.toString()))

        val kd60StartEdit = ((sectionBTable.getChildAt(B_KD_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        kd60StartEdit.setText(this.formatText(psuedoData.KD60_START_TIME))
        val kd60EndEdit = ((sectionBTable.getChildAt(B_KD_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        kd60EndEdit.setText(this.formatText(psuedoData.KD60_END_TIME))
        val kd60TempEdit = ((sectionBTable.getChildAt(B_KD_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        kd60TempEdit.setText(this.formatText(psuedoData.KD60_TEMP.toString()))
        val kd60HumEdit = ((sectionBTable.getChildAt(B_KD_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        kd60HumEdit.setText(this.formatText(psuedoData.KD60_HUMIDITY.toString()))

        val m24StartEdit = ((sectionBTable.getChildAt(B_24_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        m24StartEdit.setText(this.formatText(psuedoData.M24_START_TIME))
        val m24EndEdit = ((sectionBTable.getChildAt(B_24_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        m24EndEdit.setText(this.formatText(psuedoData.M24_END_TIME))
        val m24TempEdit = ((sectionBTable.getChildAt(B_24_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        m24TempEdit.setText(this.formatText(psuedoData.M24_TEMP.toString()))
        val m24HumEdit = ((sectionBTable.getChildAt(B_24_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        m24HumEdit.setText(this.formatText(psuedoData.M24_HUMIDITY.toString()))

        val m48StartEdit = ((sectionBTable.getChildAt(B_48_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        m48StartEdit.setText(this.formatText(psuedoData.M48_START_TIME))
        val m48EndEdit = ((sectionBTable.getChildAt(B_48_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        m48EndEdit.setText(this.formatText(psuedoData.M48_END_TIME))
        val m48TempEdit = ((sectionBTable.getChildAt(B_48_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        m48TempEdit.setText(this.formatText(psuedoData.M48_TEMP.toString()))
        val m48HumEdit = ((sectionBTable.getChildAt(B_48_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        m48HumEdit.setText(this.formatText(psuedoData.M48_HUMIDITY.toString()))

        val m72StartEdit = ((sectionBTable.getChildAt(B_72_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        m72StartEdit.setText(this.formatText(psuedoData.M72_START_TIME))
        val m72EndEdit = ((sectionBTable.getChildAt(B_72_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        m72EndEdit.setText(this.formatText(psuedoData.M72_END_TIME))
        val m72TempEdit = ((sectionBTable.getChildAt(B_72_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        m72TempEdit.setText(this.formatText(psuedoData.M72_TEMP.toString()))
        val m72HumEdit = ((sectionBTable.getChildAt(B_72_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        m72HumEdit.setText(this.formatText(psuedoData.M72_HUMIDITY.toString()))

        val m96StartEdit = ((sectionBTable.getChildAt(B_96_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        m96StartEdit.setText(this.formatText(psuedoData.M96_START_TIME))
        val m96EndEdit = ((sectionBTable.getChildAt(B_96_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        m96EndEdit.setText(this.formatText(psuedoData.M96_END_TIME))
        val m96TempEdit = ((sectionBTable.getChildAt(B_96_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        m96TempEdit.setText(this.formatText(psuedoData.M96_TEMP.toString()))
        val m96HumEdit = ((sectionBTable.getChildAt(B_96_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        m96HumEdit.setText(this.formatText(psuedoData.M96_HUMIDITY.toString()))

        val m120StartEdit = ((sectionBTable.getChildAt(B_120_ROW) as TableRow).getChildAt(B_START_COL) as EditText)
        m120StartEdit.setText(this.formatText(psuedoData.M120_START_TIME))
        val m120EndEdit = ((sectionBTable.getChildAt(B_120_ROW) as TableRow).getChildAt(B_END_COL) as EditText)
        m120EndEdit.setText(this.formatText(psuedoData.M120_END_TIME))
        val m120TempEdit = ((sectionBTable.getChildAt(B_120_ROW) as TableRow).getChildAt(B_TEMP_COL) as EditText)
        m120TempEdit.setText(this.formatText(psuedoData.M120_TEMP.toString()))
        val m120HumEdit = ((sectionBTable.getChildAt(B_120_ROW) as TableRow).getChildAt(B_HUM_COL) as EditText)
        m120HumEdit.setText(this.formatText(psuedoData.M120_HUMIDITY.toString()))

        for (iRow in 0 until this.DataTableView.tableData.nRows) {

            exposurePerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_PERFORMED_BY)))
            exposureScoreEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_SCORED_BY)))
            exposureEnterEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_DATA_ENTERED_BY)))
            kd60PerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::KD60_PERFORMED_BY)))
            kd60ScoredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::KD60_SCORED_BY)))
            kd60EnteredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::KD60_DATA_ENTERED_BY)))
            m24PerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M24_PERFORMED_BY)))
            m24ScoredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M24_SCORED_BY)))
            m24EnteredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M24_DATA_ENTERED_BY)))

            m48PerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M48_PERFORMED_BY)))
            m48ScoredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M48_SCORED_BY)))
            m48EnteredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M48_DATA_ENTERED_BY)))

            m72PerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M72_PERFORMED_BY)))
            m72ScoredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M72_SCORED_BY)))
            m72EnteredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M72_DATA_ENTERED_BY)))

            m96PerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M96_PERFORMED_BY)))
            m96ScoredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M96_SCORED_BY)))
            m96EnteredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M96_DATA_ENTERED_BY)))

            m120PerfEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M120_PERFORMED_BY)))
            m120ScoredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M120_SCORED_BY)))
            m120EnteredEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M120_DATA_ENTERED_BY)))

            acclimStartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::ACCLIMATIZATION_START_TIME)))
            acclimEndTime.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::ACCLIMATIZATION_END_TIME)))
            acclimTempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::ACCLIMATIZATION_TEMP)))
            acclimHumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::ACCLIMATIZATION_HUMIDITY)))

            exposureStartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_START_TIME)))
            exposureEndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_END_TIME)))
            exposureTempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_TEMP)))
            exposureHumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::EXPOSURE_HUMIDITY)))

            kd60StartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::KD60_START_TIME)))
            kd60EndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::KD60_END_TIME)))
            kd60TempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::KD60_TEMP)))
            kd60HumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::KD60_HUMIDITY)))

            m24StartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M24_START_TIME)))
            m24EndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M24_END_TIME)))
            m24TempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M24_TEMP)))
            m24HumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M24_HUMIDITY)))

            m48StartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M48_START_TIME)))
            m48EndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M48_END_TIME)))
            m48TempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M48_TEMP)))
            m48HumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M48_HUMIDITY)))

            m72StartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M72_START_TIME)))
            m72EndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M72_END_TIME)))
            m72TempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M72_TEMP)))
            m72HumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M72_HUMIDITY)))

            m96StartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M96_START_TIME)))
            m96EndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M96_END_TIME)))
            m96TempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M96_TEMP)))
            m96HumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M96_HUMIDITY)))

            m120StartEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M120_START_TIME)))
            m120EndEdit.addTextChangedListener(createTextWatcherString(createCallBackFor<String?>(this.DataTableView.tableData.dataArray[iRow]::M120_END_TIME)))
            m120TempEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M120_TEMP)))
            m120HumEdit.addTextChangedListener(createTextWatcherInt(createCallBackFor<Int?>(this.DataTableView.tableData.dataArray[iRow]::M120_HUMIDITY)))
        }

    }

    fun formatText(s : String?) : String? {
        if((s == "null") or  (s == null)) {
            return ""
        } else {
            return s
        }
    }


    fun completeForm(view: View) {
        val sectionATable = findViewById<TableLayout>(R.id.section_a_table)
        val sectionBTable = findViewById<TableLayout>(R.id.section_b_table)
        if (this.DataTableView.ensureNoNull(this.DataTableView) and
                this.DataTableView.ensureNoNull(sectionATable) and
                this.DataTableView.ensureNoNull(sectionBTable)) {
            this.DataTableView.tableData.metaData.completed = true
            this.DataTableView.tableData.metaData.sent = false
            writeData2Json(this.DataTableView.tableData as ConeBioassayDataTable)
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish()
            startActivity(intent)
        }  else {
            this.DataTableView.alertMissingData()
        }
    }

    override fun onBackPressed() {
        val gson = Gson()
        var returnIntent = Intent()

        returnIntent.putExtra("result", gson.toJson(this.DataTableView.tableData as ConeBioassayDataTable))
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    fun saveFormForTomorrow(view: View) {
        this.DataTableView.tableData.metaData.completed = false
        this.DataTableView.tableData.metaData.sent = false
        writeData2Json(this.DataTableView.tableData as ConeBioassayDataTable)
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

    fun writeData2Json(data: ConeBioassayDataTable) {
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

    fun <dataT> createCallBackFor(property: KMutableProperty0<dataT>): (dataT) -> (Unit) {
        val callback = fun(count: dataT): Unit { property.set(count) }
        return callback
    }

    fun createTextWatcherInt(function: (Int?) -> (Unit)): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                function(s.toString().toIntOrNull())
            }
        }
    }

    fun createTextWatcherString(function: (String?) -> (Unit)): TextWatcher {
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
}