package com.example.kaftand.entomologydatacollect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.kaftand.entomologydatacollect.CdcHdt.CdcHdt
import com.example.kaftand.entomologydatacollect.ConeBioassay.ConeBioassay
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HumanLandingCatch
import com.example.kaftand.entomologydatacollect.HutTrial.HutTrial
import com.example.kaftand.entomologydatacollect.Phase1.Phase1
import com.example.kaftand.entomologydatacollect.IndoorRestingCollection.IndoorRestingCollection
import com.example.kaftand.entomologydatacollect.IndoorRestingCollection.IndoorRestingCollectionDataTable
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import com.example.kaftand.entomologydatacollect.Util.SavedFileInfo
import kotlin.properties.Delegates


class DataEditMenu : UploadFile() {
    var formActivityClass: Class<*> by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.formActivityClass = this.getFormActivityClass(this.formTypeString)
        val uploadButton = findViewById<Button>(R.id.uploadButtonId)
        uploadButton.visibility = View.INVISIBLE
    }

    override fun fileClicked(file : SavedFileInfo)
    {
        val fileString = file.file.readText()
        var intent = Intent(this, formActivityClass)
        var bundle = Bundle()
        bundle.putString("DataTableString",fileString)
        intent.putExtra("DataTableBundle", bundle)
        startActivity(intent)
    }

    fun getFormActivityClass(formTypeString: String) : Class<*> {
        if (formTypeString == FormTypeKeys.HLC) {
            return HumanLandingCatch::class.java
        } else if (formTypeString == FormTypeKeys.HutTrial) {
            return HutTrial::class.java
        } else if (formTypeString == FormTypeKeys.CdcHdt) {
            return CdcHdt::class.java
        } else if (formTypeString == FormTypeKeys.IndoorRestingCollection) {
            return IndoorRestingCollection::class.java
        } else if (formTypeString == FormTypeKeys.ConeBioassay) {
            return ConeBioassay::class.java
        }
        else if (formTypeString == FormTypeKeys.Phase1) {
            return Phase1::class.java
        }

        return HumanLandingCatch::class.java
    }


}