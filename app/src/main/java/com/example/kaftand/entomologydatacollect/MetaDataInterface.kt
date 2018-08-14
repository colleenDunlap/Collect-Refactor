package com.example.kaftand.entomologydatacollect

import android.os.Parcelable

interface MetaDataInterface : Parcelable {
    fun stringifyInfo () : ArrayList<String>
}