package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityLoginWriteBinding
import com.example.myapplication.databinding.ActivityPlayAreaBinding
import com.example.myapplication.databinding.ActivityPlayAreaChoiceBinding

class PlayAreaChoice : AppCompatActivity() {
    private lateinit var binding: ActivityPlayAreaChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayAreaChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            val intent = Intent(this, LoginRead::class.java)
            startActivity(intent)
        }

        binding.button0.setOnClickListener {
            val intent = Intent(this, LoginWrite::class.java)
            startActivity(intent)
        }
    }
}