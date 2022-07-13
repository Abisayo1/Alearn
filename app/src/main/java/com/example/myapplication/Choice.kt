package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityChoiceBinding
import com.example.myapplication.databinding.ActivityWelcomeBinding

class Choice : AppCompatActivity() {
    private lateinit var binding: ActivityChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button0.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)

        }

        binding.button12.setOnClickListener{
            val intent = Intent(this, PlayArea::class.java)
            startActivity(intent)

        }

        binding.button3.setOnClickListener {
            val intent = Intent(this, CreateClass::class.java)
            startActivity(intent)
        }
    }
}