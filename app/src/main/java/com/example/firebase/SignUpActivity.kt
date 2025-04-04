package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                                val uid = auth.currentUser?.uid
                                val user = hashMapOf(
                                    "uid" to uid,
                                    "username" to username,
                                    "email" to email
                                )

                                val db = FirebaseFirestore.getInstance()
                                db.collection("users").document(uid!!)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Verification email sent and user data saved.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val intent = Intent(this, Category::class.java)
                                        intent.putExtra("USER_NAME", username)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            this,
                                            "Failed to save user data: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            }?.addOnFailureListener {
                                Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
