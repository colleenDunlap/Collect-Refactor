package com.example.kaftand.entomologydatacollect.IndoorRestingCollection

class IndoorRestingCollectionDataEntry (metaData: IndoorRestingCollectionMetaData) {
    public var PROJECT_CODE: String? = null
    public var DATE: String? = null
    public var DATA_ENTRY_NAME: String? = null
    public var HOUSE_NUMBER: Int? = null
    public var CLUSTER_NUMBER: Int? = null
    var HUT_NUMBER: Int? = null
    var TRAP_ID: String? = null
    var ARABIENSIS_ALIVE: Int? = null
    var ARABIENSIS_DEAD: Int? = null
    var ARABIENSIS_M24: Int? = null
    var ARABIENSIS_M48: Int? = null
    var ARABIENSIS_M72: Int? = null
    var ARABIENSIS_M96: Int? = null
    var ARABIENSIS_M120: Int? = null
    var FUNESTUS_ALIVE: Int? = null
    var FUNESTUS_DEAD: Int? = null
    var FUNESTUS_M24: Int? = null
    var FUNESTUS_M48: Int? = null
    var FUNESTUS_M72: Int? = null
    var FUNESTUS_M96: Int? = null
    var FUNESTUS_M120: Int? = null
    var CULEX_ALIVE: Int? = null
    var CULEX_DEAD: Int? = null
    var CULEX_M24: Int? = null
    var CULEX_M48: Int? = null
    var CULEX_M72: Int? = null
    var CULEX_M96: Int? = null
    var CULEX_M120: Int? = null
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