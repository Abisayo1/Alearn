package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityChoiceBinding
import com.example.myapplication.databinding.ActivityLoginFormBinding
import kotlinx.android.synthetic.main.activity_main.*

class LoginForm : AppCompatActivity() {
    private lateinit var binding: ActivityLoginFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.readBtn.setOnClickListener{
            if(binding.userName.text.toString().trim().isEmpty()){
                Toast.makeText( this,"Please enter the username of your teacher", Toast.LENGTH_SHORT).show()
            } else {
                val name = binding.userName.text.toString().trim()

                val intent = Intent(this, LoginRead::class.java)
                intent.putExtra(Eng2010Constants.USER_NAME, name)
                startActivity(intent)

        }

        binding.writeBtn.setOnClickListener {
            if (binding.userName.text.toString().trim().isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter the username of your teacher",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = binding.userName.text.toString().trim()
                val intent = Intent(this, LoginWrite::class.java)
                intent.putExtra(Eng2010Constants.USER_NAME, name)
                startActivity(intent)
            }
        }

        }
    }
}