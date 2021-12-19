package com.example.kotlinquizapp.Network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class Data ( val quiz: List<Quiz>)

@Parcelize
data class Quiz (
    val id: Long,
    val level: Long,
    val questions: @RawValue List<Question>
) : Parcelable

@Parcelize
data class Question (
    val question: String,
    val incorrectAnswers: List<String>,
    val correctAnswer: String
) : Parcelable
