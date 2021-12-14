package com.example.kotlinquizapp.Data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquizapp.Network.Data
import com.example.kotlinquizapp.Network.Quiz
import com.example.kotlinquizapp.Network.QuizRepo
import kotlinx.coroutines.launch

class MainVM : ViewModel() {


    fun fetchQuiz(): LiveData<Data> {
        val questions = MutableLiveData<Data>()

        viewModelScope.launch {
            try {
                questions.postValue(QuizRepo().fetchQuiz())

            } catch (e: Throwable) {
                Log.e("Quiz","Quiz Problem: ${e.localizedMessage}")

            }
        }
        return questions
    }

}