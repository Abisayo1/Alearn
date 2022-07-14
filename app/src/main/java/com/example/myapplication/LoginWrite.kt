package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityChoiceBinding
import com.example.myapplication.databinding.ActivityLoginWriteBinding

class LoginWrite : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}