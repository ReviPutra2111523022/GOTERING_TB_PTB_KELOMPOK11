package com.example.gotering_tb_ptb.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.gotering_tb_ptb.data.Pengguna
import com.example.gotering_tb_ptb.databinding.ActivityAddPenggunaBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddPenggunaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPenggunaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPenggunaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isEditMode = intent.getParcelableExtra<Pengguna>("data")
        if (isEditMode != null) {
            binding.etName.setText(isEditMode.name)
            binding.etAddress.setText(isEditMode.address)
            binding.etOrderType.setText(isEditMode.orderType)
            binding.etPhoneNumber.setText(isEditMode.phoneNumber)
            binding.btnTambah.text = "Simpan Perubahan"
            binding.btnDelete.isVisible = true
            binding.btnDelete.setOnClickListener {
                val db = FirebaseFirestore.getInstance()
                db.collection("pengguna").document(isEditMode.userId).delete()
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@AddPenggunaActivity,
                            "Sukses hapus data",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@AddPenggunaActivity,
                            "Gagal hapus data!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            binding.btnTambah.setOnClickListener {
                val name = binding.etName.text.toString().trim()
                val address = binding.etAddress.text.toString().trim()
                val orderType = binding.etOrderType.text.toString().trim()
                val phoneNumber = binding.etPhoneNumber.text.toString().trim()

                when {
                    name.isEmpty() -> {
                        binding.etName.error = "Nama tidak boleh kosong!"
                        binding.etName.requestFocus()
                    }

                    address.isEmpty() -> {
                        binding.etAddress.error = "Alamat tidak boleh kosong!"
                        binding.etAddress.requestFocus()
                    }

                    orderType.isEmpty() -> {
                        binding.etOrderType.error = "Jenis pesanan tidak boleh kosong!"
                        binding.etOrderType.requestFocus()
                    }

                    phoneNumber.isEmpty() -> {
                        binding.etPhoneNumber.error = "Nomor telepon tidak boleh kosong!"
                        binding.etPhoneNumber.requestFocus()
                    }

                    else -> {
                        val db = FirebaseFirestore.getInstance()
                        db.collection("pengguna").document(isEditMode.userId).set(
                            hashMapOf(
                                "name" to name,
                                "address" to address,
                                "orderType" to orderType,
                                "phoneNumber" to phoneNumber
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(
                                this@AddPenggunaActivity,
                                "Sukses edit data!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@AddPenggunaActivity,
                                "Gagal edit data!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else {
            binding.btnDelete.isVisible = false
            binding.btnTambah.setOnClickListener {
                val name = binding.etName.text.toString().trim()
                val address = binding.etAddress.text.toString().trim()
                val orderType = binding.etOrderType.text.toString().trim()
                val phoneNumber = binding.etPhoneNumber.text.toString().trim()

                when {
                    name.isEmpty() -> {
                        binding.etName.error = "Nama tidak boleh kosong!"
                        binding.etName.requestFocus()
                    }

                    address.isEmpty() -> {
                        binding.etAddress.error = "Alamat tidak boleh kosong!"
                        binding.etAddress.requestFocus()
                    }

                    orderType.isEmpty() -> {
                        binding.etOrderType.error = "Jenis pesanan tidak boleh kosong!"
                        binding.etOrderType.requestFocus()
                    }

                    phoneNumber.isEmpty() -> {
                        binding.etPhoneNumber.error = "Nomor telepon tidak boleh kosong!"
                        binding.etPhoneNumber.requestFocus()
                    }

                    else -> {
                        val db = FirebaseFirestore.getInstance()
                        db.collection("pengguna").add(
                            hashMapOf(
                                "name" to name,
                                "address" to address,
                                "orderType" to orderType,
                                "phoneNumber" to phoneNumber
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(
                                this@AddPenggunaActivity,
                                "Sukses menambahkan data!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@AddPenggunaActivity,
                                "Gagal menambahkan data!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

    }
}