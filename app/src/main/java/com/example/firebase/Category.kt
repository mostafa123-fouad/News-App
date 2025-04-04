package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivityCategoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Category : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username") ?: "User"
                    binding.tvWelcome.text = "Hello, $username"
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load user name", Toast.LENGTH_SHORT).show()
                }
        } else {
            binding.tvWelcome.text = "Hello, User"
        }

        binding.tvGeneral.setOnClickListener {
            openNews("general")
        }

        binding.tvSports.setOnClickListener {
            openNews("sports")
        }

        binding.tvTechnology.setOnClickListener {
            openNews("technology")
        }
    }

    private fun openNews(category: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }
}
