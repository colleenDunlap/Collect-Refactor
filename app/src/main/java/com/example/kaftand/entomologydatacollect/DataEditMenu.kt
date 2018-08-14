package com.example.kaftand.entomologydatacollect

import android.os.Bundle
import com.google.gson.Gson
import java.io.File


class DataEditMenu : UploadFile() {
    var iFile = 0
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)
        if (this.unsentFilesWithMeta.size > 0)
        {
            this.showFileView(this.unsentFilesWithMeta[0])
        }

    }

    fun loadFile(file : File) : Any {
        val gson = Gson()
        val requestBody = file.readText()
        return gson.fromJson(requestBody, ArrayList::class.java)
    }

    fun showFileView(fileWithInfo : SavedFileInfo) {
        var thisData = loadFile(fileWithInfo.file)
    }



}