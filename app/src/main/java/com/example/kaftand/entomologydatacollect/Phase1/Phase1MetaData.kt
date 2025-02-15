package com.example.kaftand.entomologydatacollect.Phase1

import android.os.Parcel
import android.os.Parcelable
import com.example.kaftand.entomologydatacollect.FormInterfaces.MetaDataInterface
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys

class Phase1MetaData (override var serial: Int) : MetaDataInterface {
    var DATE: String? = null
    var PROJECT_CODE: String? = "BIT032"
    var HOUSE_NUMBER: Int? = null
    var CLUSTER_NUMBER: Int? = null
    override var count: Int? = null
    var VILLAGE: String? = null
    var VOLUNTEER: String? = null
    var DATA_ENTRY_NAME: String? = null
    var WEEK: Int? = null
    var MONTH: Int? = null
    var DIRECTOR : String? = "Sarah Moore"

    override var completed = true
    override var formType = FormTypeKeys.Phase1
    override var millsCreated = System.currentTimeMillis()
    override var sent = false

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        DATE = parcel.readString()
        PROJECT_CODE = parcel.readString()
        HOUSE_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        CLUSTER_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        count = parcel.readValue(Int::class.java.classLoader) as? Int
        VILLAGE = parcel.readString()
        VOLUNTEER = parcel.readString()
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
        parcel.writeValue(count)
        parcel.writeString(VILLAGE)
        parcel.writeString(VOLUNTEER)
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

    companion object CREATOR : Parcelable.Creator<Phase1MetaData> {
        override fun createFromParcel(parcel: Parcel): Phase1MetaData {
            return Phase1MetaData(parcel)
        }

        override fun newArray(size: Int): Array<Phase1MetaData?> {
            return arrayOfNulls(size)
        }
    }

}