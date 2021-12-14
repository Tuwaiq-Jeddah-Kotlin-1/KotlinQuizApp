package com.example.kotlinquizapp.Network


data class Data ( val quiz: List<Quiz>)

data class Quiz (
    val id: Long,
    val level: Long,
    val questions: List<Question>
)

data class Question (
    val question: String,
    val incorrectAnswers: List<String>,
    val correctAnswer: String
)
