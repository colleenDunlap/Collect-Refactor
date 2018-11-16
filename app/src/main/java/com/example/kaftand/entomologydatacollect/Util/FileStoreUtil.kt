package com.example.kaftand.entomologydatacollect.Util

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.UnsupportedEncodingException

class FileStoreUtil {
    companion object {
        val sentCol = 0
        val projectCodeCol = 1
        val formTypeCol = 2
        val IP_PORT = "https://ihientodatacollection.appspot.com"
    }
    val HLC_FILE_FORMAT = arrayOf("Sent", "Project Code", "Date", "Cluster Number", "House Number", "In or out")

    fun CreateHLCFilename(sent: String?, projectCode: String?, date: String?,
                          clusterNumber:String?, houseNumber: String?, inOrOut: String?) : String {
        return (sent + "---" + projectCode + "---" + FormTypeKeys.HLC + "---" + date + "---" +
                clusterNumber + "---" + houseNumber + "---" + inOrOut)
    }

    fun createHutTrialFilename(sent: String?, date: String?) : String {
        return (sent + "---" + "null" + "---" + FormTypeKeys.HutTrial + "---" + date)

    }

    fun createGenericFilename(sent: String?, date: String?, formType: String?) : String {
        return (sent + "---" + "null" + "---" + formType + "---" + date)
    }

    fun ParseHLCFileName(filename: String) : List<String> {
        return filename.split("---")
    }

    fun uploadEachForm(callback: (m: String) -> Unit,jsonString: String, context: Context)
    {
        val requestBody = jsonString
        val requestQueue = Volley.newRequestQueue(context)
        val uRL = "$IP_PORT/HLC"
        val stringRequest = object : StringRequest(Request.Method.POST, uRL, object : Response.Listener<String> {
            override fun onResponse(response: String) {
                callback(response);
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                Log.e("VOLLEY", error.toString())
            }
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                try {
                    return requestBody?.toByteArray(Charsets.UTF_8)
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8")
                    return null
                }

            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                var responseString = ""
                if (response != null) {
                    responseString = response.statusCode.toString()
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response))
            }
        }
        requestQueue.add(stringRequest);

    }
}