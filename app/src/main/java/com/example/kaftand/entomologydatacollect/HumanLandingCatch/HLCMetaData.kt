package com.example.kaftand.entomologydatacollect.HumanLandingCatch

import android.os.Parcel
import android.os.Parcelable
import com.example.kaftand.entomologydatacollect.FormInterfaces.MetaDataInterface
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import kotlin.properties.Delegates

class HLCMetaData() : MetaDataInterface {
    override var serial = 1
    override var completed = true
    override var formType = FormTypeKeys.HLC
    override var millsCreated = System.currentTimeMillis()
    override var sent = false
    var VILLAGE: String? = null
    var DATE: String? = null
    var PROJECT_CODE: String? = null
    var HOUSE_NUMBER: Int? = null
    var VOLUNTEER_IN: String? = null
    var VOLUNTEER_OUT: String? = null
    var CLUSTER_NUMBER: Int? = null
        set(value) {
            if ((value == null) or (this.count == null)) {
                field = value
            } else
            {
                field = value
                this.serial = (field!!*1000) + this.count!!
            }
        }
    override var count: Int? = null
        set(value) {
            if ((value == null) or (this.CLUSTER_NUMBER == null)) {
                field = value
            } else
            {
                field = value
                this.serial = (this.CLUSTER_NUMBER!!*1000) + field!!
            }
        }

    constructor(parcel: Parcel) : this() {
        serial = parcel.readInt()
        completed = parcel.readByte() != 0.toByte()
        formType = parcel.readString()
        millsCreated = parcel.readLong()
        sent = parcel.readByte() != 0.toByte()
        VILLAGE = parcel.readString()
        DATE = parcel.readString()
        PROJECT_CODE = parcel.readString()
        HOUSE_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        VOLUNTEER_IN = parcel.readString()
        VOLUNTEER_OUT = parcel.readString()
    }


    override fun getFilename() : String {
        val fsu = FileStoreUtil()
        return(fsu.createGenericFilename(if(sent) {"SENT"} else {"UNSENT"}, millsCreated.toString(), this.formType))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(serial)
        parcel.writeByte(if (completed) 1 else 0)
        parcel.writeString(formType)
        parcel.writeLong(millsCreated)
        parcel.writeByte(if (sent) 1 else 0)
        parcel.writeString(VILLAGE)
        parcel.writeString(DATE)
        parcel.writeString(PROJECT_CODE)
        parcel.writeValue(HOUSE_NUMBER)
        parcel.writeString(VOLUNTEER_IN)
        parcel.writeString(VOLUNTEER_OUT)
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