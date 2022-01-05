package com.example.kotlinquizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinquizapp.R
import com.example.kotlinquizapp.ViewPagerAdapter


class QuestionsFragment : Fragment() {

    private lateinit var pager2: ViewPager2
    val arg: QuestionsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager2 = view.findViewById(R.id.viewPager)
        pager2.adapter = ViewPagerAdapter(arg.questionsArg.questions, arg.argLevel)

    }

}