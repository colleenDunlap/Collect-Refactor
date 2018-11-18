package com.example.kaftand.entomologydatacollect.FormInterfaces

import android.os.Parcelable
import android.widget.TableRow

interface MetaDataInterface : Parcelable {
    var serial : Int
    var completed : Boolean
    val formType : String
    var millsCreated: Long
    var sent: Boolean
    var count: Int?
    fun getFilename() : String
}