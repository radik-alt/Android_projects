package com.example.okapi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.okapi.R
import com.example.okapi.adapter.AdapterPerson

class PersonInformation : Fragment() {

    private lateinit var adapterP: AdapterPerson

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var groupFragment = inflater.inflate(R.layout.fragment_person_information, container, false)
        var recycler: RecyclerView = groupFragment.findViewById(R.id.recycless2)

        // Inflate the layout for this fragment
        return groupFragment
    }

}