package com.example.kotlinquizapp.Data

data class User ( val user_id: String,
                  val user_name: String,
                  val user_email: String,
                 val profile_image: String = " ",
                  val score: String = "0",
                  val currentLevel: Int = 1,
                //  val level: List<Int> = listOf(0,0,0,0,0,0)

    )