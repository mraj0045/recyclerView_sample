package com.recyclerview.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.recyclerview.sample.model.Profile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter: SampleAdapterOne = SampleAdapterOne()

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
        App.APP_DATABASE.profileDao().getAll().observe(this, Observer {
            adapter.submitList(it)
        })
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
                val profile = p0.getValue(Profile::class.java)
                profile?.let { App.APP_DATABASE.profileDao().insert(it) }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val profile = p0.getValue(Profile::class.java)
                profile?.let { App.APP_DATABASE.profileDao().insert(it) }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

                val profile = p0.getValue(Profile::class.java)
                profile?.let { App.APP_DATABASE.profileDao().delete(it) }
            }
        })
    }
}
