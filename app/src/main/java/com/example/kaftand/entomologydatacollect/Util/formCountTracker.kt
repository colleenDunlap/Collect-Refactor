package com.example.kaftand.entomologydatacollect.Util

import android.content.Context
import android.preference.PreferenceManager

object formCountTracker {
    fun readFormCount (formId: String, context: Context) : Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val count = preferences.getInt(formId, 1)
        return count
    }

    fun increaseFormCount (formId: String, context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val oldCount = this.readFormCount(formId, context)
        with (preferences.edit()) {
            putInt(formId, oldCount+1)
            commit()
        }
    }
}