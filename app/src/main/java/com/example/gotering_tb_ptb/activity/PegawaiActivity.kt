package com.example.gotering_tb_ptb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gotering_tb_ptb.PegawaiAdapter
import com.example.gotering_tb_ptb.R
import com.example.gotering_tb_ptb.data.Pegawai
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PegawaiActivity : AppCompatActivity() {
    private  lateinit var recyclerView: RecyclerView
    private  lateinit var pegawaiList: ArrayList<Pegawai>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pegawai)

        recyclerView = findViewById(R.id.pegawai_recyclerview) //-->ambil recycler view nya (di xml list item)
        recyclerView.layoutManager = LinearLayoutManager(this)

        pegawaiList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("pegawai").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val pegawai:Pegawai? = data.toObject(Pegawai::class.java)
                        if (pegawai != null){
                            pegawaiList.add(pegawai)
                        }
                    }
                    recyclerView.adapter = PegawaiAdapter(pegawaiList)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}