package com.example.kaftand.entomologydatacollect

class FileStoreUtil {
    companion object {
        val sentCol = 0
        val projectCodeCol = 1
        val formTypeCol = 2
    }
    val HLC_FILE_FORMAT = arrayOf("Sent", "Project Code", "Date", "Cluster Number", "House Number", "In or out")


    fun CreateHLCFilename(sent: String?, projectCode: String?, date: String?,
                          clusterNumber:String?, houseNumber: String?, inOrOut: String?) : String {
        return (sent + "---" + projectCode + "---" + "HLC" + "---" + date + "---" +
                clusterNumber + "---" + houseNumber + "---" + inOrOut)
    }

    fun ParseHLCFileName(filename: String) : List<String> {
        return filename.split("---")
    }
}