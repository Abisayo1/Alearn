package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
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
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTextToSpeechBinding
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_text_to_speech.*
import java.util.*


class PlayGame : AppCompatActivity() {
        var numAtp = 0
        var mMediaPlayer: MediaPlayer? = null
        var counter = (0..4).random()
            private lateinit var binding: ActivityTextToSpeechBinding
        private val model: BaseViewModel by viewModels()
        var scores = 0


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityTextToSpeechBinding.inflate(layoutInflater)
            setContentView(binding.root)


            val words = arrayOf<String>("Elephant", "Mango", "Television", "Chair", "Choir")
            val transcript = arrayOf<String>(
                "/ˈɛlɪf(ə)nt/",
                "/ˈmaŋɡəʊ/",
                "/ˈtɛlɪvɪʒ(ə)n,tɛlɪˈvɪʒ(ə)n/",
                "/tʃɛː/",
                "/ˈkwʌɪə/"
            )

            playSound()


            binding.question8.text =  words[counter]
            binding.question123.text = transcript[counter]//set the initial message.


            fun onClicks() {
                val tx = binding.question8
                val xt = binding.question123
                counter++
                if (counter >= words.size) counter = 0
                tx.text = words[counter] //set the new message.
                xt.text = transcript[counter]
            }

            fun onClick() {
                counter = (0..4).random()
                val tx = binding.question8
                val xt = binding.question123
//                if (counter >= words.size) //counter = 0
                tx.text = words[counter] //set the new message.
                xt.text = transcript[counter]
            }


            model.initial(textToSpeechEngine, startForResult)
            with(binding) {
                fabVoice.setOnClickListener { model.displaySpeechRecognizer() }

            }

            edtText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {


                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {

                   if (
                       count == 1
                   ) {edtText.text?.clear()
                    Toast.makeText(this@PlayGame, "Do not type, speak to the mic to win this level", Toast.LENGTH_SHORT).show()
                   }
                }
            })


            fabPlay.setOnClickListener {
                val edt = edtText.text.toString().trim()
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
                        onClicks()

                    } else if("${edt}" != "${binding.question8.text}") {
                        numAtp++
                        edtText.text?.clear()
                        Toast.makeText(this, "OOps!", Toast.LENGTH_SHORT).show()
                        model.speak("keep trying, you've got this!")
                        onClick()
                    }
                }
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
