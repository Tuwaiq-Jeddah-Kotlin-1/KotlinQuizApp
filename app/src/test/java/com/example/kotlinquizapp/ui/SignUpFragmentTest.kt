package com.example.kotlinquizapp.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SignUpFragmentTest {
    private lateinit var validationnn : SignUpFragment

    @Before
    fun setup() {
        validationnn = SignUpFragment()
    }

    @Test
    fun checkValidation(){
      val result = validationnn.checkEmail("ruba123@gmail.com")
        assertThat(result).isTrue()
    }
}