package com.example.kaftand.entomologydatacollect

import android.content.Context
import android.renderscript.Sampler
import android.widget.EditText
import kotlin.reflect.KProperty

class AssociativeEditText(context: Context,val propertyRepresented : KProperty<*>) : EditText(context){

}