package com.example.kaftand.entomologydatacollect.Util

import android.content.Context
import android.preference.PreferenceManager

object volunteerNameTracker {
    val preferenceName = "volunteerNames"

    fun readVolunteerNames (context: Context) : Array<String> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val names = preferences.getStringSet(preferenceName, mutableSetOf(""))
        return names.toTypedArray()
    }

    fun appendNameIfUnique (name:String, context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val oldNames = this.readVolunteerNames(context)
        var newNames = oldNames.toMutableSet()
        if (!newNames.contains(name)){
            newNames.add(name)
            with (preferences.edit()) {
                putStringSet(preferenceName, newNames)
                commit()
            }
        }
    }
}