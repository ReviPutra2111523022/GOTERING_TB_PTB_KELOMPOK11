package com.example.gotering_tb_ptb.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gotering_tb_ptb.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.submitButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            when {
                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                    binding.email.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                    binding.email.requestFocus()
                }

                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
                    binding.username.requestFocus()
                }

                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                    binding.password.requestFocus()
                }

                else -> {
                    val db = FirebaseFirestore.getInstance()
                    db.collection("akun").document(username).set(
                        hashMapOf(
                            "password" to password,
                            "username" to username,
                            "img" to "https://th.bing.com/th/id/OIP.6I8GSRo273wTecfBSQ7k5wHaHa?w=161&h=180&c=7&r=0&o=5&dpr=2&pid=1.7"
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Account Created",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val intent =
                            Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Authentication failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}