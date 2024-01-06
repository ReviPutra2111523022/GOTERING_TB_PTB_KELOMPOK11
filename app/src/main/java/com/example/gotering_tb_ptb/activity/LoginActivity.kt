package com.example.gotering_tb_ptb.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gotering_tb_ptb.MainActivity
import com.example.gotering_tb_ptb.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Credentials {

    var password: String = ""
    var username: String = ""
    var img: String =
        "https://th.bing.com/th/id/OIP.6I8GSRo273wTecfBSQ7k5wHaHa?w=161&h=180&c=7&r=0&o=5&dpr=2&pid=1.7"
}

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val usernameview = findViewById<EditText>(R.id.username)
        val passwordview = findViewById<EditText>(R.id.password)
        val submitButton = findViewById<Button>(R.id.submit_button)
        val buttonRegister = findViewById<TextView>(R.id.btnRegister)
        val buttonResetPassword = findViewById<TextView>(R.id.btnResetPassword)

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finishAffinity()
        }

        buttonResetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
            finishAffinity()
        }

        submitButton.setOnClickListener {
            val username = usernameview.text.toString()
            val password = passwordview.text.toString()

            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
                    usernameview.requestFocus()
                }

                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                    passwordview.requestFocus()
                }

                else -> {
                    // Check Firestore for the user document
                    val docRef = db.collection("akun").document(username)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                val actualPassword = document.getString("password")
                                val img = document.getString("img")
                                    ?: "https://th.bing.com/th/id/OIP.6I8GSRo273wTecfBSQ7k5wHaHa?w=161&h=180&c=7&r=0&o=5&dpr=2&pid=1.7"
                                if (actualPassword == password) {
                                    Credentials.username = username
                                    Credentials.password = password
                                    Credentials.img = img
                                    val intent = Intent(
                                        this,
                                        MainActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // Wrong password
                                    Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                // User not found
                                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}
