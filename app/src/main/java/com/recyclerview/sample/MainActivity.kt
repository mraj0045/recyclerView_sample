package com.recyclerview.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.recyclerview.sample.model.Profile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter: SampleAdapter = SampleAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        firebaseInit()
    }

    private fun init() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun firebaseInit() {
        val ref = FirebaseDatabase.getInstance().getReference("list")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //No op
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                //No op
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                adapter.update(p0.getValue(Profile::class.java))
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                adapter.add(p0.getValue(Profile::class.java))
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                adapter.remove(p0.getValue(Profile::class.java))
            }
        })
    }
}
