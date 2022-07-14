package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityTextToSpeechBinding
import com.example.myapplication.databinding.ActivityWelcomeBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class Welcome : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    lateinit var preferences: SharedPreferences
    var array: ArrayList<Int>? = null

    var mMediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)



        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val name = preferences.getString("NAME", "Mood")





        val totalQuestions = intent.getIntExtra(Eng2010Constants.TOTAL_QUESTIONS, 0)
        val correctAnswer = intent.getIntExtra(Eng2010Constants.CORRECT_ANSWERS, 0)
        array = intent.getIntegerArrayListExtra(Eng2010Constants.ONE)




        playSound()

        val button = findViewById<Button>(R.id.button3)
        val button1 = findViewById<Button>(R.id.button0)
        val text = findViewById<TextView>(R.id.question8)

        text.text = "Welcome $name! My name is Encanto and I will be your guide as you play this fun game! To get better at the game, learn your sounds first."


        button1.setOnClickListener{
            val intent = Intent(this, Choice::class.java)
            startActivity(intent)

        }

        }





    // 1. Plays the water sound
    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.up_beat)
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
}