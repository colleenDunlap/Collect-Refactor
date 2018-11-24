package com.example.kaftand.entomologydatacollect.HutTrial

import android.os.Parcel
import android.os.Parcelable
import com.example.kaftand.entomologydatacollect.FormInterfaces.MetaDataInterface
import com.example.kaftand.entomologydatacollect.Util.FileStoreUtil
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import kotlin.properties.Delegates

class HutTrialMetaData() : MetaDataInterface {
    override var serial = 1
    var PROJECT_CODE : String? = "BIT031"
    var DATE : String? = null
    var N_HUTS : Int = 1
    override var count: Int? = null
    override var completed = false
    override val formType = FormTypeKeys.HutTrial
    override var millsCreated = System.currentTimeMillis()
    override var sent = false

    constructor(parcel: Parcel) : this() {
        serial = parcel.readInt()
        PROJECT_CODE = parcel.readString()
        DATE = parcel.readString()
        N_HUTS = parcel.readInt()
        count = parcel.readValue(Int::class.java.classLoader) as? Int
        completed = parcel.readByte() != 0.toByte()
        millsCreated = parcel.readLong()
        sent = parcel.readByte() != 0.toByte()
    }


    override fun getFilename(): String {
        val fsu = FileStoreUtil()
        return (fsu.createHutTrialFilename(if(sent) {"SENT"} else {"UNSENT"}, millsCreated.toString()))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(serial)
        parcel.writeString(PROJECT_CODE)
        parcel.writeString(DATE)
        parcel.writeInt(N_HUTS)
        parcel.writeValue(count)
        parcel.writeByte(if (completed) 1 else 0)
        parcel.writeLong(millsCreated)
        parcel.writeByte(if (sent) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HutTrialMetaData> {
        override fun createFromParcel(parcel: Parcel): HutTrialMetaData {
            return HutTrialMetaData(parcel)
        }

        override fun newArray(size: Int): Array<HutTrialMetaData?> {
            return arrayOfNulls(size)
        }
    }


}