package com.example.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityLoginReadBinding
import com.example.myapplication.databinding.ActivityLoginWriteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_text_to_speech.*
import java.util.*

class LoginRead : AppCompatActivity() {
    private lateinit var binding: ActivityLoginReadBinding
    var num = 1
    var mMediaPlayer: MediaPlayer? = null
    private lateinit var database : DatabaseReference
    var numAtp = 0
    var scores = 0
    private val model: BaseViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginReadBinding.inflate(layoutInflater)
        setContentView(binding.root)



        playSound()

        model.initial(textToSpeechEngine, startForResult)
        with(binding) {
            binding.fabVoice.setOnClickListener {
                pauseSound()
                model.displaySpeechRecognizer() }

        }

        edtText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                playSound()

                if (
                    count == 1
                ) {edtText.text?.clear()
                    Toast.makeText(this@LoginRead, "Do not type, speak to the mic to win this level", Toast.LENGTH_SHORT).show()
                }
            }
        })


        binding.fabPlay.setOnClickListener {

            readData(num)

            if ("${binding.question8.text}".equals("Elephantssssssssss#@", ignoreCase = true)) {

                binding.fabPlay.text = "Done"
            } else {
            val edt = binding.edtText.text.toString().trim()
            val score = binding.score
            if (edt.isEmpty()) {
                Toast.makeText(this, "Pronounce the word to win points!", Toast.LENGTH_SHORT).show()
            }
            else {
                if ("${edt}".equals("${binding.question8.text}", ignoreCase = true)) {
                    numAtp++
                    scores++
                    Toast.makeText(this, "Well Done!", Toast.LENGTH_SHORT).show()
                    model.speak("Nice!")
                    score.text = "$scores"
                    edtText.text?.clear()


                } else if ("${edt}" != "${binding.question8.text}") {
                    numAtp++
                    edtText.text?.clear()
                    Toast.makeText(this, "OOps!", Toast.LENGTH_SHORT).show()
                    model.speak("keep trying, you've got this!")

                }
            }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
                binding.question8.isVisible = true
                num++





            }else {
                Toast.makeText(this, "It seems you are done with the test", Toast.LENGTH_SHORT).show()
                binding.fabPlay.isClickable = false
            }
        }.addOnFailureListener {

            Toast.makeText(this, "failed to retrieve", Toast.LENGTH_SHORT).show()

        }

    }



    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    .let { text -> text?.get(0) }
            binding.edtText.setText(spokenText)
        }
    }

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("en-GB")
        }
    }



    // 1. Plays the water sound
    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.game)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    // 3. Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
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