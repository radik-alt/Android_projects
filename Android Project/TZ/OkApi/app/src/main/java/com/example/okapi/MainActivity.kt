package com.example.okapi

import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.okapi.Database.DbManager
import com.example.okapi.adapter.AdapterPerson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.nio.Buffer

class MainActivity : AppCompatActivity() {

    private lateinit var recycle: RecyclerView
    private var btn: Button? = null
    private lateinit var adapterP: AdapterPerson
    private var list: ArrayList<Models> = ArrayList()
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // инициализация
        init()
        //toolbar
        setSupportActionBar(toolbar)


        //api и json
        val api = Newtwork()
        api.execute()
        var result = api.get()
        val json: JSONObject = JSONObject(result)
        JSON(json)

        //DB
        readDB()
    }


    // обработка json
    fun JSON (json: JSONObject) {
        val jsonarr: JSONArray = json.getJSONArray("response")
        val l = jsonarr.length() - 1
        for (i in 0..l){
            val first = jsonarr.getJSONObject(i)
            val f_name = first.getString("f_name")
            val l_name = first.getString("l_name")
            val birthday = first.getString("birthday")

            val t_spec = first.getJSONArray("specialty")
            val t_spec2 = t_spec.getJSONObject(0)
            val spec = t_spec2.getString("name")

            list.add(Models(f_name, l_name, birthday, spec))
        }
        adapt()
    }

    // работа с бд
    fun readDB () {
        var db = DbManager(this)
        db.open()

        var c = 0
        for (i in list){
            db.insert(list[c].first_name, list[c].last_name, list[c].birthday, list[c].position)
            c++
        }

        var arr = ArrayList<String>()
        arr = db.readData()

        for(i in arr) {
            Log.d("MYTAGS", i)
        }
        db.close()
    }


    // адаптер
    fun adapt () {

        adapterP = AdapterPerson(this, list)
        val lmanager: RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false)
        recycle.layoutManager = lmanager
        recycle.adapter = adapterP
    }

    // инициализация
    fun init () {
        toolbar = findViewById(R.id.toolbar)
        recycle = findViewById(R.id.recycless)
    }

    // меню поиска
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search: MenuItem? = menu?.findItem(R.id.search)
        val searcView: SearchView = search?.actionView as SearchView
        searcView.queryHint = "Search"

        return super.onCreateOptionsMenu(menu)
    }
}