package com.example.kotlinquizapp.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuizBuilder {
    private const val BASE_URL = "http://qy36e.mocklab.io"
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val quizApi: QuizAPI = retrofit().create(QuizAPI::class.java)
}