package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityCreateClassBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_class.*

class CreateClass : AppCompatActivity() {
    var numAtp = 1
    var list = 0
    private lateinit var binding : ActivityCreateClassBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createBtn.setOnClickListener{
            val name = binding.userName.text.toString().trim()
            val num = numAtp
            val word = binding.word.text.toString().trim()
            val trans = binding.trans.text.toString().trim()

            database = FirebaseDatabase.getInstance().getReference(name)
            val User = User(num, word, trans)
            database.child("$num").setValue(User).addOnSuccessListener {
                binding.userName.setText(name)
                binding.userName.isFocusable = false
                binding.word.text.clear()
                binding.trans.text.clear()
                numAtp++
                binding.num.setText("$numAtp")



                Toast.makeText(this, "Successfully Created!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "failed to create class!", Toast.LENGTH_SHORT).show()
            }


        }
    }
}