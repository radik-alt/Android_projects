package com.example.whatsapp.Messager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityMessageWindowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageWindow : AppCompatActivity() {

    lateinit var bindingMess: ActivityMessageWindowBinding
    var messAdapter: RecycleMessage? = null
    val database = FirebaseDatabase.getInstance()
    private var currentId: String? = null
    private var firebaseAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMess = ActivityMessageWindowBinding.inflate(layoutInflater)
        setContentView(bindingMess.root)
        init()


        val ref = database.getReference("chats").child(currentId!!)
        var list: ArrayList<String> = ArrayList()

        bindingMess.send.setOnClickListener {
            ref.setValue(bindingMess.text.text.toString())
//            list.add(bindingMess.text.text.toString())
            bindingMess.text.setText("")
            onChangeList(ref, list)
        }

        // адаптер
        messAdapter = RecycleMessage(list, this)
        adapt()
    }

    private fun onChangeList (ref: DatabaseReference, list: ArrayList<String>) {
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.setValue("")
                list.add(snapshot.value.toString())
                messAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun adapt () {
        var coursManager : RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false)

        bindingMess.recycleMessage.layoutManager = coursManager
        bindingMess.recycleMessage.adapter = messAdapter
    }

    private fun init () {
        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth?.getCurrentUser()?.getUid();
    }
}