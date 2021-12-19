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

//    private lateinit var optionOne: Button
//    private lateinit var optionTwo: Button
//    private lateinit var optionThree: Button
//    private lateinit var optionFour: Button
    private lateinit var cancel: ImageView
    private lateinit var nextQuestion: Button

    private lateinit var pager2: ViewPager2
    private lateinit var tvScore: TextView
    private lateinit var tvLevel: TextView
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
        //tvScore = view.findViewById(R.id.tvScore)
        //tvLevel = view.findViewById(R.id.tvLevel)





        pager2.adapter = ViewPagerAdapter(arg.questionsArg.questions, arg.argLevel)







//        question = view.findViewById(R.id.tvQuestion)
//        optionOne = view.findViewById(R.id.btnOption1)
//        optionTwo = view.findViewById(R.id.btnOption2)
//        optionThree = view.findViewById(R.id.btnOption3)
//        optionFour = view.findViewById(R.id.btnOption4)
//        cancel = view.findViewById(R.id.ivCancel)
//        nextQuestion = view.findViewById(R.id.btnNextQuestion)
//
//        question.text = arg.questionsArg.questions[0].question
//        optionOne.text = arg.questionsArg.questions[0].correctAnswer
//        optionTwo.text = arg.questionsArg.questions[0].incorrectAnswers[0]
//        optionThree.text = arg.questionsArg.questions[0].incorrectAnswers[1]
//        optionFour.text = arg.questionsArg.questions[0].incorrectAnswers[2]



    }


}