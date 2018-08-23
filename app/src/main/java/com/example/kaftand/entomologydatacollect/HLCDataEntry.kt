package com.example.kaftand.entomologydatacollect

import java.io.Serializable
import java.sql.Date

class HLCDataEntry (metaData: HLCMetaData) : DataEntryInterface {
    public var VILLAGE: String? = null
    public var PROJECT_CODE: String? = null
    public var DATE: String? = null
    public var DATA_ENTRY_NAME: String? = null
    public var HOUSE_NUMBER: Int? = null
    public var CLUSTER_NUMBER: Int? = null
    public var VOLUNTEER_NUMBER: Int? = null
    public var IN_OR_OUT: String? = null
    public var GAMBIAE: Int? = 0
    public var FUNESTUS: Int? = 0
    public var COUSTANI: Int? = 0
    public var CULEX: Int? = 0
    public var MANSONIA: Int? = 0
    public var AEDES: Int? = 0
    public var COQUILETTIDIA: Int? = 0
    public var OTHER: Int? = 0
    public var HOUR: String? = null

    init {
        VILLAGE = metaData.VILLAGE
        PROJECT_CODE = metaData.PROJECT_CODE
        DATE = metaData.DATE
        DATA_ENTRY_NAME = metaData.DATA_ENTRY_NAME
        HOUSE_NUMBER = metaData.HOUSE_NUMBER
        CLUSTER_NUMBER = metaData.CLUSTER_NUMBER
        IN_OR_OUT = metaData.IN_OR_OUT
        if (metaData.IN_OR_OUT == "in")
        {
            VOLUNTEER_NUMBER = metaData.VOLUNTEER_NUMBER_IN
        } else
        {
            VOLUNTEER_NUMBER = metaData.VOLUNTEER_NUMBER_OUT
        }
    }

}