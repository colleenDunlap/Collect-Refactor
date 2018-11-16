package com.example.kaftand.entomologydatacollect.FormInterfaces

import android.os.Parcelable
import android.widget.TableRow

interface MetaDataInterface : Parcelable {
    var serial : Int
    var completed : Boolean
    val formType : String
    var millsCreated: Long
    var sent: Boolean
    fun getFilename() : String
}