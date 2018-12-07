package com.example.kaftand.entomologydatacollect.ConeBioassay

import android.os.Parcel
import android.os.Parcelable
import com.example.kaftand.entomologydatacollect.FormInterfaces.MetaDataInterface
import com.example.kaftand.entomologydatacollect.R
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys

class ConeBioassayMetaData(override var serial: Int) : MetaDataInterface {

    override fun getFilename(): String {
        val fsu = FileStoreUtil()
        return (fsu.createGenericFilename(if(sent) {"SENT"} else {"UNSENT"}, millsCreated.toString(), FormTypeKeys.ConeBioassay))
    }

    var STUDY_DIRECTOR = "Sarah Moore"
    var DATE: String? = null
    var HOUSE_NUMBER: Int? = null
    var IRS_CODE: String? = null
    var TEMPERATURE: Int? = null
    var HUMIDITY: Int? = null
    var MOSQUITO_STRAIN: String? = null
    var MOSQUITO_AGE_MIN: Int? = null
    var MOSQUITO_AGE_MAX: Int? = null
    var VILLAGE: String? = null
    var CLUSTER_NUMBER: Int? = null
    override var count: Int? = null
    var PROJECT_CODE: String? = "BIT031"
    override var completed = true
    override var formType = FormTypeKeys.ConeBioassay
    override var millsCreated = System.currentTimeMillis()
    override var sent = false

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        STUDY_DIRECTOR = parcel.readString()
        DATE = parcel.readString()
        HOUSE_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        IRS_CODE = parcel.readString()
        TEMPERATURE = parcel.readValue(Int::class.java.classLoader) as? Int
        HUMIDITY = parcel.readValue(Int::class.java.classLoader) as? Int
        MOSQUITO_STRAIN = parcel.readString()
        MOSQUITO_AGE_MIN = parcel.readValue(Int::class.java.classLoader) as? Int
        MOSQUITO_AGE_MAX = parcel.readValue(Int::class.java.classLoader) as? Int
        VILLAGE = parcel.readString()
        CLUSTER_NUMBER = parcel.readValue(Int::class.java.classLoader) as? Int
        count = parcel.readValue(Int::class.java.classLoader) as? Int
        PROJECT_CODE = parcel.readString()
        completed = parcel.readByte() != 0.toByte()
        formType = parcel.readString()
        millsCreated = parcel.readLong()
        sent = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(serial)
        parcel.writeString(STUDY_DIRECTOR)
        parcel.writeString(DATE)
        parcel.writeValue(HOUSE_NUMBER)
        parcel.writeString(IRS_CODE)
        parcel.writeValue(TEMPERATURE)
        parcel.writeValue(HUMIDITY)
        parcel.writeString(MOSQUITO_STRAIN)
        parcel.writeValue(MOSQUITO_AGE_MIN)
        parcel.writeValue(MOSQUITO_AGE_MAX)
        parcel.writeString(VILLAGE)
        parcel.writeValue(CLUSTER_NUMBER)
        parcel.writeValue(count)
        parcel.writeString(PROJECT_CODE)
        parcel.writeByte(if (completed) 1 else 0)
        parcel.writeString(formType)
        parcel.writeLong(millsCreated)
        parcel.writeByte(if (sent) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConeBioassayMetaData> {
        override fun createFromParcel(parcel: Parcel): ConeBioassayMetaData {
            return ConeBioassayMetaData(parcel)
        }

        override fun newArray(size: Int): Array<ConeBioassayMetaData?> {
            return arrayOfNulls(size)
        }
    }


}