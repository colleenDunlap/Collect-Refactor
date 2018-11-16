package com.example.kaftand.entomologydatacollect.Util


object TableConstants  {
    fun firstEntry(iRow: Int) : Int {
        return (stringToInt("${iRow}f"))
    }
    fun lastEntry(iRow: Int) : Int {
        return (stringToInt("${iRow}l"))
    }

    fun stringToInt(s : String) : Int {
        val ca = s.toCharArray()
        var string = ""
        for (i in 0 until ca.size) {
            val thisInt = ca[i].toInt()
            string += thisInt.toString()
        }
        return string.toInt()
    }
}