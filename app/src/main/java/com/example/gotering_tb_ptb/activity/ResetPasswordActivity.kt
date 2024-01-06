package com.example.gotering_tb_ptb.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gotering_tb_ptb.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //auth = FirebaseAuth.getInstance()

        //kembali ke halaman login.
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.submitButton.setOnClickListener {
            val email = binding.email.text.toString().trim()

            when {
                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                    binding.email.requestFocus()
                }

                //else -> {
                //    val db = FirebaseFirestore.getInstance()
                //db.collection("akun").document(email).set(
                //    hashMapOf(
                //        "img" to "https://th.bing.com/th/id/OIP.6I8GSRo273wTecfBSQ7k5wHaHa?w=161&h=180&c=7&r=0&o=5&dpr=2&pid=1.7"
                //    )
                //    ).addOnSuccessListener {
                //        Toast.makeText(
                //            this@ResetPasswordActivity,
                //            "Password berhasil direset.",
                //            Toast.LENGTH_SHORT
                //        )
                //            .show()
                //        val intent =
                //            Intent(applicationContext, LoginActivity::class.java)
                //        startActivity(intent)
                //        finish()
                //    }.addOnFailureListener {
                //        Toast.makeText(
                //            this@ResetPasswordActivity,
                //            "Password gagal direset.",
                //            Toast.LENGTH_SHORT
                //        ).show()
                //}
                // }
            }
        }
    }
}