package com.example.kotlinquizapp.Network

import retrofit2.http.GET

interface QuizAPI {
    @GET("http://qy36e.mocklab.io/thing/1")
    suspend fun fetchQuestions(): Data
}