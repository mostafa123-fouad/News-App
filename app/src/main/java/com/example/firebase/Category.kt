package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.databinding.ActivityCategoryBinding

class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding=ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val username = intent.getStringExtra("USER_NAME") ?: "User"
        binding.tvWelcome.text = "Hello, $username"
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
