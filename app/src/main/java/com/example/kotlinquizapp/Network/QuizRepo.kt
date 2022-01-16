package com.example.kotlinquizapp.Network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRepo {
    //
    private val api = QuizBuilder.quizApi

    suspend fun fetchQuiz(): Data = withContext(Dispatchers.IO) {
        api.fetchQuestions()
    }
}