package com.example.gotering_tb_ptb.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gotering_tb_ptb.KelolaAdapter
import com.example.gotering_tb_ptb.data.Pengguna
import com.example.gotering_tb_ptb.databinding.ActivityKelolaBinding
import com.google.firebase.firestore.FirebaseFirestore

class KelolaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKelolaBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getAllUsers()
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddPenggunaActivity::class.java))
        }
    }

    private fun getAllUsers() {
        val users = ArrayList<Pengguna>()
        val docRef = db.collection("pengguna")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = document.data
                    val name = data["name"] as String
                    val address = data["address"] as String
                    val orderType = data["orderType"] as String
                    val phoneNumber = data["phoneNumber"] as String
                    users.add(Pengguna(document.id, name, address, orderType, phoneNumber))
                }
                binding.rvKelola.layoutManager = LinearLayoutManager(this)
                binding.rvKelola.adapter = KelolaAdapter(users.sortedBy { it.name }) {
                    val intent = Intent(this, AddPenggunaActivity::class.java).apply {
                        putExtra("data", it)
                    }
                    startActivity(intent)
                }
                if (documents.isEmpty) {
                    binding.tvEmptyData.isVisible = true
                    binding.rvKelola.isVisible = false
                } else {
                    binding.tvEmptyData.isVisible = false
                    binding.rvKelola.isVisible = true
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        getAllUsers()
    }
}