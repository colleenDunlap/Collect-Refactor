package com.example.kaftand.entomologydatacollect.HumanLandingCatch

import android.os.Parcel
import android.os.Parcelable
import com.example.kaftand.entomologydatacollect.FormInterfaces.MetaDataInterface
import com.example.kaftand.entomologydatacollect.R

class HLCMetaData() : MetaDataInterface {
    override var serial = 1
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

    fun stringifyInfo() : ArrayList<String>
    {
        var infoArray : ArrayList<String> = ArrayList<String>()
        if (this.IN_OR_OUT == "in")
        {
            infoArray.add("${R.string.indoor}")
        } else {
            infoArray.add("${R.string.outdoor}")
        }
        infoArray.add("${R.string.village} ${this.VILLAGE}")
        infoArray.add("${R.string.day_month_year} ${this.DATE}")
        infoArray.add("${R.string.project_code} ${this.PROJECT_CODE}")
        infoArray.add("${R.string.house_number} ${this.HOUSE_NUMBER}")
        infoArray.add("${R.string.cluster_number} ${this.CLUSTER_NUMBER}")
        infoArray.add("${R.string.volunteer_number_inside} ${this.VOLUNTEER_NUMBER_IN}")
        infoArray.add("${R.string.volunteer_number_outside} ${this.VOLUNTEER_NUMBER_OUT}")
        return infoArray
    }



}