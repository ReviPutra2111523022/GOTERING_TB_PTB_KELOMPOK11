package com.example.gotering_tb_ptb.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.gotering_tb_ptb.R
import com.example.gotering_tb_ptb.activity.Credentials.username
import com.example.gotering_tb_ptb.databinding.ActivityEditBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private val REQUEST_CODE_STORAGE_PERMISSION =
        123 // Nilai bebas, dapat diganti dengan nilai lain

    private var resultLauncher: ActivityResultLauncher<String>? = null
    private lateinit var firebaseFireStore: FirebaseFirestore
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null

    private val db = FirebaseFirestore.getInstance()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Meminta izin READ_EXTERNAL_STORAGE jika belum diizinkan oleh pengguna
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Jika izin belum diberikan, minta izin kepada pengguna
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        }

        storageReference = FirebaseStorage.getInstance().reference.child("Images")
        firebaseFireStore = FirebaseFirestore.getInstance()

        // Set the toolbar as the action bar for the activity
        binding.toolbar4.title = ""

        setSupportActionBar(binding.toolbar4)

        // Add a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    // Image selected, proceed with upload
                    imageUri = uri
                    uploadImage()
                } else {
                    // Handle case when no image is selected
                    Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                }
            }

        binding.myImageView.setOnClickListener {
            resultLauncher?.launch("image/*")
        }

        binding.submitButton.setOnClickListener {
            val oldpassword = binding.oldpassword.text.toString().trim()
            val newpassword = binding.newpassword.text.toString().trim()

            if (username.isNotEmpty()) {
                // Check Firestore for the user document
                val docRef = db.collection("akun").document(username)
                docRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val actualPassword = document.getString("password")
                        val img = document.getString("img") ?: ""
                        if (actualPassword == oldpassword) {
                            val newCredentials = hashMapOf<String, Any>(
                                "password" to newpassword
                            )
                            // Update the password field in Firestore
                            docRef.update(newCredentials)
                                .addOnSuccessListener {
                                    // Update successful
                                    Credentials.username = username
                                    Credentials.password = newpassword
                                    Credentials.img = img

                                    val intent = Intent(
                                        this,
                                        com.example.gotering_tb_ptb.activity.LoginActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    // Update failed
                                    Toast.makeText(
                                        this,
                                        "Password update failed: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            // Wrong password
                            Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // User not found
                        Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter username and password!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun uploadImage() {
        storageReference = storageReference.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageReference.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()

                        val userDocRef =
                            firebaseFireStore.collection("akun").document(Credentials.username)

                        // Check if the document exists
                        userDocRef.get().addOnCompleteListener { userTask ->
                            if (userTask.isSuccessful) {
                                val document = userTask.result
                                if (document.exists()) {
                                    // Update existing document's "img" field
                                    userDocRef.update("img", imageUrl)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Image URL updated successfully",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            Glide.with(this)
                                                .load(imageUrl)
                                                .into(binding.myImageView)
                                            Credentials.img = imageUrl
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this,
                                                "Failed to update image URL: ${e.message}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                } else {
                                    // Create new document with "img" field
                                    val userData = hashMapOf(
                                        "img" to imageUrl
                                    )
                                    userDocRef.set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Image URL added successfully",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this,
                                                "Failed to add image URL: ${e.message}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to get document: ${userTask.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Failed to upload image: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val imageview = findViewById<ImageView>(R.id.my_image_view)
        Glide.with(this)
            .load(Credentials.img)
            .into(imageview)
    }


}