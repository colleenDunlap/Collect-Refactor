package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.widget.TextView
import kotlin.reflect.KProperty


class AssociativeTextView(context: Context, val propertyRepresented : KProperty<*>) : TextView(context){
}