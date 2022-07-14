package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityChoiceBinding
import com.example.myapplication.databinding.ActivityLoginFormBinding

class LoginForm : AppCompatActivity() {
    private lateinit var binding: ActivityLoginFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readBtn.setOnClickListener{
            val intent = Intent(this, LoginRead::class.java)
            startActivity(intent)

        }

        binding.writeBtn.setOnClickListener{
            val intent = Intent(this, LoginWrite::class.java)
            startActivity(intent)

        }
    }
}