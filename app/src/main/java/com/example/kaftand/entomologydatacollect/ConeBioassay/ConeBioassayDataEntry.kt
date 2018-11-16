package com.example.kaftand.entomologydatacollect.ConeBioassay

import com.example.kaftand.entomologydatacollect.HumanLandingCatch.HLCMetaData

class ConeBioassayDataEntry (metaData: ConeBioassayMetaData) {
    var serial: String? = null
    var EXPOSURE_PERFORMED_BY: String? = null
    var EXPOSURE_SCORED_BY: String? = null
    var EXPOSURE_DATA_ENTERED_BY: String? = null
    var KD60_PERFORMED_BY: String? = null
    var KD60_SCORED_BY: String? = null
    var KD60_DATA_ENTERED_BY: String? = null
    var M24_PERFORMED_BY: String? = null
    var M24_SCORED_BY: String? = null
    var M24_DATA_ENTERED_BY: String? = null
    var M48_PERFORMED_BY: String? = null
    var M48_SCORED_BY: String? = null
    var M48_DATA_ENTERED_BY: String? = null
    var M72_PERFORMED_BY: String? = null
    var M72_SCORED_BY: String? = null
    var M72_DATA_ENTERED_BY: String? = null
    var M96_PERFORMED_BY: String? = null
    var M96_SCORED_BY: String? = null
    var M96_DATA_ENTERED_BY: String? = null
    var M120_PERFORMED_BY: String? = null
    var M120_SCORED_BY: String? = null
    var M120_DATA_ENTERED_BY: String? = null
    var STUDY_DIRECTOR = "Sarah Moore"
    var DATE: String? = null
    var HOUSE_NUMBER: Int? = null
    var IRS_CODE: String? = null
    var TEMPERATURE: Int? = null
    var HUMIDITY: Int? = null
    var MOSQUITO_STRAIN: String? = null
    var MOSQUITO_AGE_MIN: Int? = null
    var MOSQUITO_AGE_MAX: Int? = null
    var ACCLIMATIZATION_START_TIME: String? = null
    var ACCLIMATIZATION_END_TIME: String? = null
    var ACCLIMATIZATION_TEMP: Int? = null
    var ACCLIMATIZATION_HUMIDITY: Int? = null
    var EXPOSURE_START_TIME: String? = null
    var EXPOSURE_END_TIME: String? = null
    var EXPOSURE_TEMP: Int? = null
    var EXPOSURE_HUMIDITY: Int? = null
    var KD60_START_TIME: String? = null
    var KD60_END_TIME: String? = null
    var KD60_TEMP: Int? = null
    var KD60_HUMIDITY: Int? = null
    var M24_START_TIME: String? = null
    var M24_END_TIME: String? = null
    var M24_TEMP: Int? = null
    var M24_HUMIDITY: Int? = null
    var M48_START_TIME: String? = null
    var M48_END_TIME: String? = null
    var M48_TEMP: Int? = null
    var M48_HUMIDITY: Int? = null
    var M72_START_TIME: String? = null
    var M72_END_TIME: String? = null
    var M72_TEMP: Int? = null
    var M72_HUMIDITY: Int? = null
    var M96_START_TIME: String? = null
    var M96_END_TIME: String? = null
    var M96_TEMP: Int? = null
    var M96_HUMIDITY: Int? = null
    var M120_START_TIME: String? = null
    var M120_END_TIME: String? = null
    var M120_TEMP: Int? = null
    var M120_HUMIDITY: Int? = null
    var PROJECT_CODE: String? = null
    var CLUSTER_NUMBER: Int? = null

    var REPLICATE: Int? = null
    var EXPOSURE_START: String? = null
    var EXPOSURE_END: String? = null
    var N_EXPOSED: Int? = null
    var A1: Int? = null
    var M1: Int? = null
    var A24: Int? = null
    var M24: Int? = null
    var A48: Int? = null
    var M48: Int? = null
    var A72: Int? = null
    var M72: Int? = null
    var A96: Int? = null
    var M96: Int? = null
    var A120: Int? = null
    var M120: Int? = null


    init {
        EXPOSURE_PERFORMED_BY = metaData.EXPOSURE_PERFORMED_BY
        EXPOSURE_SCORED_BY = metaData.EXPOSURE_SCORED_BY
        EXPOSURE_DATA_ENTERED_BY = metaData.EXPOSURE_DATA_ENTERED_BY
        KD60_PERFORMED_BY = metaData.KD60_PERFORMED_BY
        KD60_SCORED_BY = metaData.KD60_SCORED_BY
        KD60_DATA_ENTERED_BY = metaData.KD60_DATA_ENTERED_BY
        M24_PERFORMED_BY = metaData.M24_PERFORMED_BY
        M24_SCORED_BY = metaData.M24_SCORED_BY
        M24_DATA_ENTERED_BY = metaData.M24_DATA_ENTERED_BY
        M48_PERFORMED_BY = metaData.M48_PERFORMED_BY
        M48_SCORED_BY = metaData.M48_SCORED_BY
        M48_DATA_ENTERED_BY = metaData.M48_DATA_ENTERED_BY
        M72_PERFORMED_BY = metaData.M72_PERFORMED_BY
        M72_SCORED_BY = metaData.M72_SCORED_BY
        M72_DATA_ENTERED_BY = metaData.M72_DATA_ENTERED_BY
        M96_PERFORMED_BY = metaData.M96_PERFORMED_BY
        M96_SCORED_BY = metaData.M96_SCORED_BY
        M96_DATA_ENTERED_BY = metaData.M96_DATA_ENTERED_BY
        M120_PERFORMED_BY = metaData.M120_PERFORMED_BY
        M120_SCORED_BY = metaData.M120_SCORED_BY
        M120_DATA_ENTERED_BY = metaData.M120_DATA_ENTERED_BY
        STUDY_DIRECTOR = metaData.STUDY_DIRECTOR
        DATE = metaData.DATE
        HOUSE_NUMBER = metaData.HOUSE_NUMBER
        IRS_CODE = metaData.IRS_CODE
        TEMPERATURE = metaData.TEMPERATURE
        HUMIDITY = metaData.HUMIDITY
        MOSQUITO_STRAIN = metaData.MOSQUITO_STRAIN
        PROJECT_CODE = metaData.PROJECT_CODE
        MOSQUITO_AGE_MAX = metaData.MOSQUITO_AGE_MAX
        MOSQUITO_AGE_MIN = metaData.MOSQUITO_AGE_MIN
    }

}