package com.example.kotlinquizapp

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizapp.Network.Question
import com.example.kotlinquizapp.Network.Quiz

class ViewPagerAdapter(
    var data: List<Question>
, var level: String
): RecyclerView.Adapter<Pager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
        return Pager2ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        var quizQuestion = data[position]



        holder.question.text = quizQuestion.question

         holder.tvLevel.text = level

        //make it random

        val list = listOf(
            quizQuestion.correctAnswer,
            quizQuestion.incorrectAnswers[0],
            quizQuestion.incorrectAnswers[1],
            quizQuestion.incorrectAnswers[2]
        )

        val numberOfElements = 4

        val randomElement = list.asSequence().shuffled().take(numberOfElements).toList()



        holder.option1.text = randomElement[0]
        holder.option2.text = randomElement[1]
        holder.option3.text = randomElement[2]
        holder.option4.text = randomElement[3]
        val buttons = listOf(holder.option1,holder.option2,holder.option3,holder.option4)

        // while (counter == 0 ) {
        holder.option1.setOnClickListener {


            checkAnswers(holder.option1, buttons, quizQuestion.correctAnswer)

//                val drawble = holder.option1.background
//                val wrapedDrawable = DrawableCompat.wrap(drawble)
//                DrawableCompat.setTint(wrapedDrawable, Color.GREEN)


        }

        holder.option2.setOnClickListener {

            checkAnswers(holder.option2,buttons, quizQuestion.correctAnswer)

        }

        holder.option3.setOnClickListener {
            checkAnswers(holder.option3,buttons, quizQuestion.correctAnswer)
        }

            holder.option4.setOnClickListener {
                checkAnswers(holder.option4,buttons, quizQuestion.correctAnswer)
            }
        }

            override fun getItemCount(): Int {
                return data.size
            }

    fun checkAnswers(btn:Button,listOfbtn: List<Button>, answer: String){


        if (btn.text == answer) {
            btn.setBackgroundColor(Color.GREEN)
//
        } else {
            btn.setBackgroundColor(Color.RED)

        }

        listOfbtn.forEach {
            it.isEnabled = false
        }

    }
}



class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val question: TextView = itemView.findViewById(R.id.tvQuestion)
    val option1: Button = itemView.findViewById(R.id.btnOption1)
    val option2: Button = itemView.findViewById(R.id.btnOption2)
    val option3: Button = itemView.findViewById(R.id.btnOption3)
    val option4: Button = itemView.findViewById(R.id.btnOption4)
    val tvLevel: TextView = itemView.findViewById(R.id.tvLevel)

}