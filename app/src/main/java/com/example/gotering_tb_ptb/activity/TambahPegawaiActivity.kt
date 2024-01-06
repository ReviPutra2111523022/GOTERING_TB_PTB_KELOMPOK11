package com.example.gotering_tb_ptb.activity

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import com.example.gotering_tb_ptb.R
import com.google.firebase.firestore.FirebaseFirestore
import com.example.gotering_tb_ptb.data.Product
import android.content.Intent
import android.widget.TextView
import android.widget.EditText
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.content.Context
import java.util.*
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.gotering_tb_ptb.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gotering_tb_ptb.activity.Credentials.img
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TambahPegawaiActivity : AppCompatActivity() {
    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTelepon: EditText
    private lateinit var etJabatan: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSave: Button

    //private lateinit var progressBar: ProgressBar

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pegawai)

        etNama = findViewById(R.id.stv_nama)
        etAlamat = findViewById(R.id.stv_alamat)
        etTelepon = findViewById(R.id.stv_telepon)
        etJabatan = findViewById(R.id.stv_jabatan)
        etDeskripsi = findViewById(R.id.stv_deskripsi)
        btnSave = findViewById(R.id.sbtn_save)
        //progressBar = findViewById(R.id.progressBar)

        btnSave.setOnClickListener {
            val sNama = etNama.text.toString().trim()
            val sAlamat = etAlamat.text.toString().trim()
            val sTelepon = etTelepon.text.toString().trim()
            val sJabatan = etJabatan.text.toString().trim()
            val sDeskripsi = etDeskripsi.text.toString().trim()


            val pegawaiMap = hashMapOf(
                "nama" to sNama,
                "alamat" to sAlamat,
                "telepon" to sTelepon,
                "jabatan" to sJabatan,
                "deskripsi" to sDeskripsi)


            db.collection("pegawai").document().set(pegawaiMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Data pegawai baru berhasil ditambahkan.", Toast.LENGTH_SHORT).show()
                    etNama.text.clear()
                    etAlamat.text.clear()
                    etTelepon.text.clear()
                    etJabatan.text.clear()
                    etDeskripsi.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Data pegawai baru gagal ditambahkan.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}