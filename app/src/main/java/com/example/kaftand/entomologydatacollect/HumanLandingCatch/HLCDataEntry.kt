package com.example.kaftand.entomologydatacollect.HumanLandingCatch

class HLCDataEntry (metaData: HLCMetaData, InOrOut: String?) {
    var serial = 1
    public var VILLAGE: String? = null
    public var PROJECT_CODE: String? = null
    public var DATE: String? = null
    public var HOUSE_NUMBER: Int? = null
    public var CLUSTER_NUMBER: Int? = null
    public var VOLUNTEER: String? = null
    public var IN_OR_OUT: String? = null
    public var GAMBIAE: Int? = null
    public var FUNESTUS: Int? = null
    public var COUSTANI: Int? = null
    public var CULEX: Int? = null
    public var MANSONIA: Int? = null
    public var AEDES: Int? = null
    public var COQUILETTIDIA: Int? = null
    public var OTHER: Int? = null
    public var HOUR: String? = null

    init {
        this.IN_OR_OUT = InOrOut
        updateFromMetaData(metaData)
    }

    fun updateFromMetaData(metaData: HLCMetaData) {
        serial = metaData.serial
        VILLAGE = metaData.VILLAGE
        PROJECT_CODE = metaData.PROJECT_CODE
        DATE = metaData.DATE
        HOUSE_NUMBER = metaData.HOUSE_NUMBER
        CLUSTER_NUMBER = metaData.CLUSTER_NUMBER
        VOLUNTEER = if(IN_OR_OUT == "in") {metaData.VOLUNTEER_IN} else {metaData.VOLUNTEER_OUT}
    }

}