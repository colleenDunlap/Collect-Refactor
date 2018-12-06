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


class ViewFormFromServerDialog() : DialogFragment() {
    var formType = FormTypeKeys.HLC
    var usernameHandle by Delegates.notNull<EditText>()
    var passwordHandle by Delegates.notNull<EditText>()
    var serialHandle by Delegates.notNull<EditText>()
    var contextSaved = context
    var finished = false
    var gson = Gson()
    private var dialogListener by Delegates.notNull<Returning>()
    init {
        this.formType
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        formType = getArguments()!!.getString("formType");
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            var view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_view_form_from_server, null)
            usernameHandle = view.findViewById<EditText>(R.id.username)
            passwordHandle = view.findViewById<EditText>(R.id.password)
            serialHandle = view.findViewById<EditText>(R.id.serial)
            builder.setView(view)
                    // Add action buttons
            .setPositiveButton("Get Data",
                    DialogInterface.OnClickListener { dialog, id ->
                        var username = usernameHandle.text.toString()
                        var password = passwordHandle.text.toString()
                        var serial = serialHandle.text.toString()
                        dialogListener.return_value(username, password, serial)


                    })
            .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog().cancel()
                            })

            builder.create()



        } ?: throw IllegalStateException("Activity cannot be null")
    }


    companion object {
        fun newInstance(formType: String): ViewFormFromServerDialog {
            val f = ViewFormFromServerDialog()

            // Supply num input as an argument.
            val args = Bundle()
            args.putString("formType", formType)
            f.setArguments(args)

            return f
        }
    }



    interface Returning {
        fun return_value(username: String, password: String, serial: String)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.dialogListener = context as Returning
    }
}