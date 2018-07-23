package com.example.kaftand.entomologydatacollect

import android.os.Parcel
import android.os.Parcelable
import java.sql.Date

class HLCMetaData() : Parcelable {
    public var VILLAGE: String? = null
    public var DATE: String? = null
    public var PROJECT_CODE: String? = null
    public var HOUSE_NUMBER: Int? = null
    public var CLUSTER_NUMBER: Int? = null
    public var VOLUNTEER_NUMBER_IN: Int? = null
    public var VOLUNTEER_NUMBER_OUT: Int? = null
    public var DATA_ENTRY_NAME: String? = null
    public var IN_OR_OUT: String? = null

    constructor(parcel: Parcel) : this() {
        VILLAGE = parcel.readString()
        DATE = parcel.readString()
        PROJECT_CODE = parcel.readString()
        HOUSE_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        CLUSTER_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        VOLUNTEER_NUMBER_IN = parcel.readValue(Int::class.java.classLoader) as? Int
        VOLUNTEER_NUMBER_OUT = parcel.readValue(Int::class.java.classLoader) as? Int
        DATA_ENTRY_NAME = parcel.readString()
        IN_OR_OUT = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(VILLAGE)
        parcel.writeString(DATE)
        parcel.writeString(PROJECT_CODE)
        parcel.writeValue(HOUSE_NUMBER)
        parcel.writeValue(CLUSTER_NUMBER)
        parcel.writeValue(VOLUNTEER_NUMBER_IN)
        parcel.writeValue(VOLUNTEER_NUMBER_OUT)
        parcel.writeString(DATA_ENTRY_NAME)
        parcel.writeString(IN_OR_OUT)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HLCMetaData> {
        override fun createFromParcel(parcel: Parcel): HLCMetaData {
            return HLCMetaData(parcel)
        }

        override fun newArray(size: Int): Array<HLCMetaData?> {
            return arrayOfNulls(size)
        }
    }


}