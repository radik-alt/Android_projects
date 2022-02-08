package com.example.okapi.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmenttz.Interface.InterfacePerson
import com.example.fragmenttz.Models
import com.example.fragmenttz.R
import com.example.okapi.Database.DbManager
import com.example.okapi.adapter.AdapterPerson
import com.example.okapi.adapter.AdapterPosition
import java.util.ArrayList

class Person : Fragment() {

    private lateinit var adapterP: AdapterPerson
    private lateinit var recycle: RecyclerView
    private lateinit var list: ArrayList<Models>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val group = inflater.inflate(R.layout.fragment_person, container, false)

        recycle = group.findViewById(R.id.recycless3)
        list = ArrayList()

        var param = arguments?.getString(params1) ?: "NO"
        Log.d("BUNDLERESULT", param)

//        list.add(Models("Радик", "Абдлухаков", "31-10-2002", "Менеджер"))

        var db = DbManager(requireContext())
        db.open()
        var arr = ArrayList<Models>()
        arr = db.readData().toList() as ArrayList<Models>
        db.close()

        for (i in arr){
            if (i.position.equals(param)){
                list.add(i)
            }
        }

        adapt()
        // Inflate the layout for this fragment
        return group
    }

    fun adapt () {
        adapterP = AdapterPerson(requireContext(), list, object : InterfacePerson{
            override fun onClick(f_name: String, l_name: String, birthday: String) {
                changeFragment(f_name, l_name, birthday)
            }

        })
        val lmanager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL, false)
        recycle.layoutManager = lmanager
        recycle.adapter = adapterP
    }

    fun changeFragment (f_name: String, l_name: String, birthday: String) {
        var fragment: Fragment = Info.newInstance(f_name, l_name, birthday)
        parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }

    companion object {

        @JvmStatic
        private val params1: String = "PARAMONE"

        @JvmStatic
        fun newInstance(param1: String) =
            Person().apply {
                arguments = Bundle().apply {
                    putString(params1, param1)
                }
            }
    }

}