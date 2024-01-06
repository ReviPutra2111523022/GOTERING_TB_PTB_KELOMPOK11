package com.example.gotering_tb_ptb.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gotering_tb_ptb.R
import com.example.gotering_tb_ptb.activity.Credentials
import com.example.gotering_tb_ptb.MainActivity
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentActivity = activity

        if (parentActivity != null && parentActivity is MainActivity) {
            val username = Credentials.username
            val password = Credentials.password
            val img = Credentials.img

            val usernameview = view?.findViewById<TextView>(R.id.nameview)
            usernameview?.text = username

            val imageview = view?.findViewById<ImageView>(R.id.image_view)
            imageview?.let { imageView ->
                Glide.with(this)
                    .load(img)
                    .into(imageView)
            }

            val exitbutton = view?.findViewById<Button>(R.id.exit_button)
            exitbutton?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.LoginActivity::class.java
                )
                startActivity(intent)
            }

            val btnKelola = view?.findViewById<Button>(R.id.btn_kelola)
            btnKelola?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.KelolaActivity::class.java
                )
                startActivity(intent)
            }

            val btnPegawai = view?.findViewById<Button>(R.id.btn_pegawai)
            btnPegawai?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.TambahPegawaiActivity::class.java
                )
                startActivity(intent)

            }

            val btnPegawai2 = view?.findViewById<Button>(R.id.btn_pegawai2)
            btnPegawai2?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.PegawaiActivity::class.java
                )
                startActivity(intent)
            }

            val tentangbutton = view?.findViewById<Button>(R.id.tentang_button)
            tentangbutton?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.TentangKamiActivity::class.java
                )
                startActivity(intent)
            }

            val rekapButton = view?.findViewById<Button>(R.id.transaksi_button)
            rekapButton?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.RekapActivity::class.java
                )
                startActivity(intent)
            }

            val editButton = view?.findViewById<Button>(R.id.edit_button)
            editButton?.setOnClickListener {
                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.activity.EditActivity::class.java
                )
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val imageview = view?.findViewById<ImageView>(R.id.image_view)
        imageview?.let {
            Glide.with(this)
                .load(Credentials.img)
                .into(it)
        }

    }


}
