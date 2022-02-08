package com.example.fragmenttz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.okapi.Database.DbManager
import com.example.okapi.Database.DbScheme
import com.example.okapi.Fragment.Info
import com.example.okapi.Fragment.Person
import com.example.okapi.Fragment.PositionJob
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<Models> = ArrayList()
    private lateinit var set: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //api и json
        val api = Newtwork()
        api.execute()
        var result = api.get()
        JSON(result)

    }


    // обработка json
    fun JSON (result: String) {

        val json: JSONObject = JSONObject(result)
        val jsonarr: JSONArray = json.getJSONArray("response")
        val l = jsonarr.length() - 1

        var g = 0
        for (i in 0..l){

            val first = jsonarr.getJSONObject(i)
            var f_name = first.getString("f_name")
            var l_name = first.getString("l_name")
            var birthday = first.getString("birthday")
            val t_spec = first.getJSONArray("specialty")
            val t_spec2 = t_spec.getJSONObject(0)
            var spec = t_spec2.getString("name")

            var age: Int = 0
            // convert имен и фамилий
            f_name = convert(f_name)
            l_name = convert(l_name)

            // convert даты
            if (!validDate(birthday)) {
                birthday = "<<"
                age = 0
            } else {
                if (g < 3) {
                    birthday = convertDate1(birthday)
                    g++
                } else {
                    birthday = convertDate2(birthday)
                }
                age = getAge(birthday)

            }

            Log.d("AGEEEEEEE", age.toString())

            list.add(Models(f_name, l_name, birthday, age, spec))
        }
        readDB()
    }

    // определение возраста
    fun getAge (birthday: String): Int {

        var birth = Calendar.getInstance()
        var today = Calendar.getInstance()

        var t = birthday.split(".")
        var day = t[0].toInt()
        var mouth = t[1].toInt()
        var year = t[2].toInt()
        birth.set(year, mouth, day)

        var ages = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1

//        Log.d("BIRTHDAY", "${today.get(Calendar.YEAR)} - ${birth.get(Calendar.YEAR)}")

        if (birth.get(Calendar.MONTH) < today.get(Calendar.MONTH)) {
            ages++
        } else if (birth.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
            if (birth.get(Calendar.DAY_OF_MONTH) <= today.get(Calendar.DAY_OF_MONTH)) {
                ages++
            }
        }

        return ages
    }

    // conver даты
    fun convertDate1 (birthday: String) : String{
        var parser : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd");
        var formatter : SimpleDateFormat  = SimpleDateFormat("dd.MM.yyyy");
        var res = formatter.format(parser.parse(birthday));

        return res
    }

    fun convertDate2 (birthday: String) : String{
        var parsers : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy");
        var formatters : SimpleDateFormat  = SimpleDateFormat("dd.MM.yyyy");
        var res = formatters.format(parsers.parse(birthday));

        return res
    }

    // проверка даты
    fun validDate (birthday: String) : Boolean {
        var birthdayTemp: String = birthday
        if (birthdayTemp.equals("null") or birthdayTemp.equals("")){
            return false
        }
        return true
    }

    // convert имен и фамилий
    fun convert (name: String) : String {
        var output = ""
        for (i in 0 until name.length) {
            if (!name[i].isUpperCase() && i == 0) {
                output += name[i].toUpperCase()
            } else if (name[i].isUpperCase() && i == 0) {
                output += name[i].toUpperCase()
            }else {
                output += name[i].toLowerCase()
            }
        }

        return output
    }

    // работа с бд
    fun readDB () {
        var db = DbManager(this)
        db.open()
        db.sqlDB?.delete(DbScheme.TABLE_NAME, null, null)
        var c = 0
        for (i in list){
            db.insert(list[c].first_name, list[c].last_name, list[c].birthday, list[c].age,
                list[c].position )
            c++
        }
        db.close()
    }


}