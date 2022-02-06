package com.example.okapi

import android.os.AsyncTask
import android.util.Log
import com.example.okapi.Database.DbManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class Newtwork() : AsyncTask<String, String, String>() {

    private val tag : String = "MYTAGS"
    private val URLS: String = "https://gitlab.65apps.com/65gb/static/raw/master/testTask.json"

    override fun onPreExecute() {
        Log.d(tag, "START")
        super.onPreExecute()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg p0: String?): String {
        var res: String = ""

        val client: OkHttpClient = OkHttpClient()
        var request: Request = Request.Builder()
            .url(URLS)
            .build()

        var response: Response = client.newCall(request).execute()

        if (response.isSuccessful) {
            res = response.body()!!.string()
        } else {
            throw IOException("Unexpected code " + response);
        }
        return res
    }
}