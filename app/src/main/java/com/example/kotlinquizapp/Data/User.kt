package com.example.kotlinquizapp.Data

data class User ( val user_id: String,
                  val user_name: String,
                  val user_email: String,
                  val profile_image: String = " ",
                  val score: Int = 0,
                  val currentLevel: Int = 1,
                  val level: Array<Int> = arrayOf(0,0,0,0,0,0)
    )