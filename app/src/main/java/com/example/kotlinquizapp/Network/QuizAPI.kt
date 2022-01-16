package com.example.kotlinquizapp.Network

import retrofit2.http.GET

interface QuizAPI {
    @GET("/thing/1")
    suspend fun fetchQuestions(): Data
}