package com.example.kaftand.entomologydatacollect.CdcHdt

import android.os.Parcel
import android.os.Parcelable
import com.example.kaftand.entomologydatacollect.FormInterfaces.MetaDataInterface
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys

class CdcHdtMetaData() : MetaDataInterface {
    override var serial = 1
    var DATE: String? = null
    var PROJECT_CODE: String? = null
    var HOUSE_NUMBER: Int? = null
    var CLUSTER_NUMBER: Int? = null
    var VOLUNTEER_NUMBER: Int? = null
    var DATA_ENTRY_NAME: String? = null
    var WEEK: Int? = null
    var MONTH: Int? = null
    var DIRECTOR : String? = "Sarah Moore"
    override var completed = true
    override var formType = FormTypeKeys.CdcHdt
    override var millsCreated = System.currentTimeMillis()
    override var sent = false

    constructor(parcel: Parcel) : this() {
        serial = parcel.readInt()
        DATE = parcel.readString()
        PROJECT_CODE = parcel.readString()
        HOUSE_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        CLUSTER_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        VOLUNTEER_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        DATA_ENTRY_NAME = parcel.readString()
        WEEK = parcel.readValue(Int::class.java.classLoader) as? Int
        MONTH = parcel.readValue(Int::class.java.classLoader) as? Int
        DIRECTOR = parcel.readString()
        completed = parcel.readByte() != 0.toByte()
        formType = parcel.readString()
        millsCreated = parcel.readLong()
        sent = parcel.readByte() != 0.toByte()
    }


    override fun getFilename() : String {
        val fsu = FileStoreUtil()
        return(fsu.createGenericFilename(if(sent) {"SENT"} else {"UNSENT"}, millsCreated.toString(), this.formType))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(serial)
        parcel.writeString(DATE)
        parcel.writeString(PROJECT_CODE)
        parcel.writeValue(HOUSE_NUMBER)
        parcel.writeValue(CLUSTER_NUMBER)
        parcel.writeValue(VOLUNTEER_NUMBER)
        parcel.writeString(DATA_ENTRY_NAME)
        parcel.writeValue(WEEK)
        parcel.writeValue(MONTH)
        parcel.writeString(DIRECTOR)
        parcel.writeByte(if (completed) 1 else 0)
        parcel.writeString(formType)
        parcel.writeLong(millsCreated)
        parcel.writeByte(if (sent) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CdcHdtMetaData> {
        override fun createFromParcel(parcel: Parcel): CdcHdtMetaData {
            return CdcHdtMetaData(parcel)
        }

        override fun newArray(size: Int): Array<CdcHdtMetaData?> {
            return arrayOfNulls(size)
        }
    }
}