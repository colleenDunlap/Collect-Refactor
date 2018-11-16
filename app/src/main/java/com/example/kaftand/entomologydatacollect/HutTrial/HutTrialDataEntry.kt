package com.example.kaftand.entomologydatacollect.HutTrial


class HutTrialDataEntry (metaData: HutTrialMetaData) {
    var HUT_NUMBER : Int? = null
    var TRAP_ID : String? = null
    var NET_NUMBER : Int? = null
    var VOLUNTEER_NUMBER : Int? = null
    var GAMBIAE_MORNING_UNFED_ALIVE : Int? = null
    var GAMBIAE_MORNING_UNFED_DEAD : Int? = null
    var GAMBIAE_MORNING_FED_ALIVE : Int? = null
    var GAMBIAE_MORNING_FED_DEAD : Int? = null
    var GAMBIAE_24HR_UNFED_ALIVE : Int? = null
    var GAMBIAE_24HR_UNFED_DEAD : Int? = null
    var GAMBIAE_24HR_FED_ALIVE : Int? = null
    var GAMBIAE_24HR_FED_DEAD : Int? = null
    var FUNESTUS_MORNING_UNFED_ALIVE : Int? = null
    var FUNESTUS_MORNING_UNFED_DEAD : Int? = null
    var FUNESTUS_MORNING_FED_ALIVE : Int? = null
    var FUNESTUS_MORNING_FED_DEAD : Int? = null
    var FUNESTUS_24HR_UNFED_ALIVE : Int? = null
    var FUNESTUS_24HR_UNFED_DEAD : Int? = null
    var FUNESTUS_24HR_FED_ALIVE : Int? = null
    var FUNESTUS_24HR_FED_DEAD : Int? = null
    var CULEX_MORNING_UNFED_ALIVE : Int? = null
    var CULEX_MORNING_UNFED_DEAD : Int? = null
    var CULEX_MORNING_FED_ALIVE : Int? = null
    var CULEX_MORNING_FED_DEAD : Int? = null
    var CULEX_24HR_UNFED_ALIVE : Int? = null
    var CULEX_24HR_UNFED_DEAD : Int? = null
    var CULEX_24HR_FED_ALIVE : Int? = null
    var CULEX_24HR_FED_DEAD : Int? = null
    var OTHER_FEMALE_COUNT : Int? = null
    var OTHER_GENUS : Int? = null
    var PROJECT_CODE : String? = null
    var DATE : String? = null

    init {
        PROJECT_CODE = metaData.PROJECT_CODE
        DATE = metaData.DATE
    }
}