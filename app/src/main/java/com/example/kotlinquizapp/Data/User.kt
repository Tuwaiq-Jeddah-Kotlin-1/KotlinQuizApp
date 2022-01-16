package com.example.kotlinquizapp.Data

data class User ( val user_id: String,
                  val user_name: String,
                  val user_email: String,
                  val profile_image: String = " ",
                  val score: String = "0",
                  val currentLevel: Long = 1,
                  val nextLevel: Long = 1
)

data class ScoreLevel (
    val score1 : Long = 0,
    val score2 : Long = 0,
    val score3 : Long = 0,
    val score4 : Long = 0,
    val score5 : Long = 0
        )
