package com.example.kaftand.entomologydatacollect

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import com.example.kaftand.entomologydatacollect.Util.FormTypeKeys
import com.google.gson.Gson
import kotlin.properties.Delegates


class TabletNumberDialog() : DialogFragment() {
    private var dialogListener by Delegates.notNull<Returning>()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            var view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tablet_number, null)
            val usernameHandle = view.findViewById<EditText>(R.id.tablet)
            builder.setView(view)
                    // Add action buttons
                    .setPositiveButton("Set Number",
                            DialogInterface.OnClickListener { dialog, id ->
                                var username = usernameHandle.text.toString()
                                dialogListener.tablet_return_value(username.toInt())

                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog().cancel()
                            })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    companion object {
        fun newInstance(): TabletNumberDialog {
            val f = TabletNumberDialog()

            return f
        }
    }

    interface Returning {
        fun tablet_return_value(tabletNumber: Int)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.dialogListener = context as Returning
    }
}