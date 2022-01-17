package com.example.kotlinquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class SplashScreen : AppCompatActivity() {

     private lateinit var topAnim: Animation
     private lateinit var bottomAnim: Animation

     private lateinit var tvKQ: TextView
     private lateinit var tvKotlinQuiz: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

            supportActionBar?.hide()

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

         tvKQ = findViewById(R.id.tvKQ)
         tvKotlinQuiz = findViewById(R.id.tvKotlinQuiz)

        tvKQ.animation = topAnim
        tvKotlinQuiz.animation = bottomAnim

            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                //code
               startActivity(Intent(this, MainActivity::class.java))
            }, 2000)

        }
    }
