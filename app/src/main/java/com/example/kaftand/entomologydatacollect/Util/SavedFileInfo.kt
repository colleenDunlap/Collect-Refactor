package com.example.kaftand.entomologydatacollect.Util

import java.io.File

class SavedFileInfo {
    var formType: String? = null
    var sent: String? = null
    var studyCode: String? = null
    var file: File = File("")

    fun setMetaDataFromFileName(file: File) {
        val parsedFile = file.name.split("---")
        this.sent = parsedFile[FileStoreUtil.sentCol]
        this.formType = parsedFile[FileStoreUtil.formTypeCol]
        this.file = file
    }

    fun reNameFileAfterSent()
    {
        var parsedFile = this.file.name.split("---") as MutableList
        parsedFile[FileStoreUtil.sentCol] = "SENT"
        val newFileName = parsedFile.joinToString("---")
        val directory = this.file.parent
        val newFile = File(directory, newFileName)
        this.file.renameTo(newFile)
    }


}