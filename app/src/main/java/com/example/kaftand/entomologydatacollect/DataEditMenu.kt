package com.example.kaftand.entomologydatacollect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HumanLandingCatch
import com.example.kaftand.entomologydatacollect.Util.SavedFileInfo
import kotlin.properties.Delegates


class DataEditMenu : UploadFile() {
    var formActivityClass: Class<*> by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.formActivityClass = this.getFormActivityClass(this.formTypeString)
    }

    override fun fileClicked(file : SavedFileInfo)
    {
        Log.i("EDEEET",file.studyCode)
        val fileString = file.file.readText()
        var intent = Intent(this, formActivityClass)
        var bundle = Bundle()
        bundle.putString("DataTableString",fileString)
        intent.putExtra("DataTableBundle", bundle)
        startActivity(intent)
    }

    fun getFormActivityClass(formTypeString: String) : Class<*> {
        if (formTypeString == getString(R.string.human_landing_catch)) {
            return HumanLandingCatch::class.java
        } else if (formTypeString == getString(R.string.hut_study)) {
            return HumanLandingCatch::class.java
        }

        return HumanLandingCatch::class.java
    }


}