package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityChoiceBinding
import com.example.myapplication.databinding.ActivityLoginFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class LoginForm : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityLoginFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userName.text.toString().trim()


        binding.readBtn.setOnClickListener {
            val name = binding.userName.text.toString().trim()
            database = FirebaseDatabase.getInstance().getReference(name)
            database.child("1").get().addOnSuccessListener {
                if (it.exists()) {
                    val intent = Intent(this, LoginRead::class.java)
                    intent.putExtra(Eng2010Constants.USER_NAME, name)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "This class does not exist, please enter the correct username of your teacher",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }.addOnFailureListener {
                Toast.makeText(this, "failed to create class!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.writeBtn.setOnClickListener {
            val name = binding.userName.text.toString().trim()
            database = FirebaseDatabase.getInstance().getReference(name)
            database.child("1").get().addOnSuccessListener {
                if (it.exists()) {
                        val intent = Intent(this, LoginWrite::class.java)
                        intent.putExtra(Eng2010Constants.USER_NAME, name)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "This class does not exist, please enter the correct username of your teacher",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }.addOnFailureListener {
                Toast.makeText(this, "failed to create class!", Toast.LENGTH_SHORT).show()
            }

            }
        }
    }

