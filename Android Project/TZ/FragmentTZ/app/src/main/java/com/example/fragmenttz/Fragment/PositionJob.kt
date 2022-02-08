package com.example.okapi.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmenttz.Interface.InterfacePosition
import com.example.fragmenttz.Models
import com.example.fragmenttz.R
import com.example.okapi.Database.DbManager
import com.example.okapi.adapter.AdapterPosition
import java.util.ArrayList

class PositionJob : Fragment() {

    private lateinit var adapterP: AdapterPosition
    private lateinit var recycle: RecyclerView
    private lateinit var list: ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var groupFragment = inflater.inflate(R.layout.fragment_person_information, container, false)
        recycle = groupFragment.findViewById(R.id.recycless2)
        list = ArrayList()


        var db = DbManager(requireContext())
        db.open()
        var arr = ArrayList<Models>()
        arr = db.readData().toList() as ArrayList<Models>

        var arr2 : MutableSet<String> = db.getPosition()
        for (i in arr2) {
            Log.d("POSITIONS", i)
        }
        db.close()

        list.addAll(arr2.toList())

        adapt()
        return groupFragment
    }

    // адаптер
    fun adapt () {

        adapterP = AdapterPosition(requireContext(), list, object : InterfacePosition{
            override fun onClick(position: String) {
                Log.d("INTERFACELISTENER", position)
                changeFragment(position)
            }

        })
        val lmanager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL, false)
        recycle.layoutManager = lmanager
        recycle.adapter = adapterP
    }

    fun changeFragment (position: String) {
        var fragment: Fragment = Person.newInstance(position)
        parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }

    companion object {

        @JvmStatic
        fun newInstance() = PositionJob()
    }
}