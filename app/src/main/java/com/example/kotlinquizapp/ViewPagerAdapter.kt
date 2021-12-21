package com.example.kotlinquizapp

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.kotlinquizapp.Network.Question
import com.example.kotlinquizapp.Network.Quiz
import com.example.kotlinquizapp.ui.QuestionsFragmentDirections
import com.example.kotlinquizapp.ui.firebaseFirestore
import com.example.kotlinquizapp.ui.firebaseUserId
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ViewPagerAdapter(var arg: Quiz,var data: List<Question> , var level: String): RecyclerView.Adapter<Pager2ViewHolder>() {

      var  score: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {


        val v = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)




        firebaseFirestore.collection("users")
            .document(firebaseUserId)
            .addSnapshotListener(object: EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null ) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )
                        return
                    } else {
                        score = value!!.get("score").toString().toInt()
                    }
                }

            })


        return Pager2ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        var quizQuestion = data[position]

        Log.e("adapter", "$position")

        holder.question.text = quizQuestion.question
        holder.tvLevel.text = level
        holder.tvScore.text = score.toString()

        holder.submitBtn.visibility = View.GONE

        if (position ==  getItemCount() - 1) {
            holder.submitBtn.visibility = View.VISIBLE

            //fun
            val pass = (score/getItemCount())*100

            Log.e("AdapterPager", "$pass")
            holder.submitBtn.setOnClickListener {
                if (pass >= 50) {
                    val action = QuestionsFragmentDirections.actionQuestionsFragmentToLevelUpFragment()
                    it.findNavController().navigate(action)
                } else {
                    val action = QuestionsFragmentDirections.actionQuestionsFragmentToTryAgainFragment(arg)
                    it.findNavController().navigate(action)
                }
            }
        }




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

    fun checkAnswers(btn:Button,listOfbtn: List<Button>, answer: String){

        if (btn.text == answer) {
            btn.setBackgroundColor(Color.GREEN)
            score = score+1
            firebaseFirestore.collection("users").document(firebaseUserId)
                .update("score",score.toString())
        } else {
            btn.setBackgroundColor(Color.RED)

        }
        listOfbtn.forEach {
            it.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val question: TextView = itemView.findViewById(R.id.tvQuestion)
    val option1: Button = itemView.findViewById(R.id.btnOption1)
    val option2: Button = itemView.findViewById(R.id.btnOption2)
    val option3: Button = itemView.findViewById(R.id.btnOption3)
    val option4: Button = itemView.findViewById(R.id.btnOption4)
    val tvLevel: TextView = itemView.findViewById(R.id.tvLevel)
    val tvScore: TextView = itemView.findViewById(R.id.tvScore)
    val submitBtn: Button = itemView.findViewById(R.id.btnSubmit)

}