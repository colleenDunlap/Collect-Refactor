package com.example.kaftand.entomologydatacollect.CdcHdt

class CdcHdtDataEntry (metaData: CdcHdtMetaData) {
    public var PROJECT_CODE: String? = null
    public var DATE: String? = null
    public var DATA_ENTRY_NAME: String? = null
    public var HOUSE_NUMBER: Int? = null
    public var CLUSTER_NUMBER: Int? = null
    var HUT_NUMBER: Int? = null
    var TRAP_ID: String? = null
    var ARABIENSIS_UNFED_ALIVE: Int? = null
    var ARABIENSIS_UNFED_DEAD: Int? = null
    var ARABIENSIS_FED_ALIVE: Int? = null
    var ARABIENSIS_FED_DEAD: Int? = null
    var FUNESTUS_UNFED_ALIVE: Int? = null
    var FUNESTUS_UNFED_DEAD: Int? = null
    var FUNESTUS_FED_ALIVE: Int? = null
    var FUNESTUS_FED_DEAD: Int? = null
    var CULEX_UNFED_ALIVE: Int? = null
    var CULEX_UNFED_DEAD: Int? = null
    var CULEX_FED_ALIVE: Int? = null
    var CULEX_FED_DEAD: Int? = null
    var OTHER_FEMALE: Int? = null
    var OTHER_SPECIES: String? = null
    public var WEEK: Int? = null
    public var MONTH: Int? = null
    public var STUDY_DIRECTOR: String? = null
    public var SERIAL: Int? = null

    init {
        PROJECT_CODE = metaData.PROJECT_CODE
        DATE = metaData.DATE
        DATA_ENTRY_NAME = metaData.DATA_ENTRY_NAME
        HOUSE_NUMBER = metaData.HOUSE_NUMBER
        CLUSTER_NUMBER = metaData.CLUSTER_NUMBER
        WEEK = metaData.WEEK
        MONTH = metaData.MONTH
        STUDY_DIRECTOR = metaData.DIRECTOR
        SERIAL = metaData.serial

    }

}