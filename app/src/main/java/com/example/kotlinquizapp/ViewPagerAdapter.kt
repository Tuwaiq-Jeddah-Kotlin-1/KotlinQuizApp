package com.example.kotlinquizapp

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizapp.Network.Question
import com.example.kotlinquizapp.ui.QuestionsFragmentDirections
import com.example.kotlinquizapp.ui.firebaseFirestore
import com.example.kotlinquizapp.ui.firebaseUserId
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class ViewPagerAdapter(var data: List<Question> , var level: String ): RecyclerView.Adapter<Pager2ViewHolder>() {


    var score: Int? = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
                    Log.e("adapteeer","user id $firebaseUserId")


        firebaseFirestore.collection("users")
            .document(firebaseUserId)
            .addSnapshotListener(object: EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null ) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )

                    } else {
                        score = value!!.getString("score")?.toInt()
                        Log.e("scoreadapter","$score") }
                }
            })

        return Pager2ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        var quizQuestion = data[position]

        holder.question.text = quizQuestion.question
        holder.tvLevel.text = level
        holder.tvScore.text = score.toString()

        holder.submitBtn.visibility = View.GONE

        if (position==getItemCount()-1){
            holder.submitBtn.visibility = View.VISIBLE
            //val pass = (score/getItemCount())*100
            val pass = 100

            Log.e("AdapterPager", "$pass")
            holder.submitBtn.setOnClickListener {
                if (pass >= 50) {
                    val action = QuestionsFragmentDirections.actionQuestionsFragmentToLevelUpFragment()
                    it.findNavController().navigate(action)
                } else {
                    val action = QuestionsFragmentDirections.actionQuestionsFragmentToTryAgainFragment()
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

    fun checkAnswers(btn:Button,listOfbtn: List<Button>, answer: String,
                    // score: Int
    ){
        if (btn.text == answer) {
            btn.setBackgroundColor(Color.GREEN)
            Log.e("score","$score")

            score = score?.plus(1)
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