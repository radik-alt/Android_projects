package com.example.okapi.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmenttz.Models
import com.example.fragmenttz.R
import com.example.okapi.Database.DbManager
import com.example.okapi.adapter.AdapterInfo
import com.example.okapi.adapter.AdapterPerson
import com.example.okapi.adapter.AdapterPosition
import java.util.ArrayList


class Info : Fragment() {

    private lateinit var adapterP: AdapterInfo
    private lateinit var recycle: RecyclerView
    private lateinit var list: ArrayList<Models>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val group = inflater.inflate(R.layout.fragment_info, container, false)

        recycle = group.findViewById(R.id.recycless4)
        list = ArrayList()

//        list.add(Models("Радик", "Абдлухаков", "31-10-2002", "Менеджер"))


        var db = DbManager(requireContext())
        db.open()
        var arr = ArrayList<Models>()
        arr = db.readData().toList() as ArrayList<Models>
        db.close()

        var f_name = arguments?.getString(params1) ?: "NO"
        var l_name = arguments?.getString(params2) ?: "NO"
        var birth = arguments?.getString(params3) ?: "<<"

        Log.d("BUNDLERESULT", "$f_name $l_name $birth")

        for (i in arr){
            if (i.first_name.equals(f_name) and i.last_name.equals(l_name)
                and i.birthday.equals(birth)){

                list.add(i)
                Log.d("LOGGGGG", i.toString())
            }
        }

        adapt()
        return group
    }

    fun adapt () {

        adapterP = AdapterInfo(requireContext(), list)
        val lmanager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL, false)
        recycle.layoutManager = lmanager
        recycle.adapter = adapterP
    }


    companion object {

        @JvmStatic
        private val params1: String = "PARAMONE"

        @JvmStatic
        private val params2: String = "PARAMTWO"

        @JvmStatic
        private val params3: String = "PARAMTHREE"

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            Info().apply {
                arguments = Bundle().apply {
                    putString(params1, param1)
                    putString(params2, param2)
                    putString(params3, param3)
                }
            }
    }


}