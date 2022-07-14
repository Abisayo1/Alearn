package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityChoiceBinding
import com.example.myapplication.databinding.ActivityLoginWriteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginWrite : AppCompatActivity() {
    var num = 1
    private lateinit var database : DatabaseReference
    var numAtp = 0
    var scores = 0
    private lateinit var binding: ActivityLoginWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        binding.done.setOnClickListener {

            readData(num)

        }



        }

    private fun readData(number: Int) {
        var name = intent.getStringExtra(Eng2010Constants.USER_NAME).toString()

        database = FirebaseDatabase.getInstance().getReference(name)
        database.child("$number").get().addOnSuccessListener {

            if (it.exists()) {

                val word = it.child("word").value
                val trans = it.child("transcription").value

                binding.question8.text = word as CharSequence?
                binding.question123.text = trans as CharSequence?
                binding.question123.isVisible = true
                binding.done.text = "Done"
                num++

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()


            }else {
                Toast.makeText(this, "It seems you are done with the test", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {

            Toast.makeText(this, "failed to retrieve", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId
        return if (id == R.id.chkResult) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Eng2010Constants.CORRECT_ANSWERS, scores)
            intent.putExtra(Eng2010Constants.TOTAL_QUESTIONS, numAtp)
            startActivity(intent)
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }
}