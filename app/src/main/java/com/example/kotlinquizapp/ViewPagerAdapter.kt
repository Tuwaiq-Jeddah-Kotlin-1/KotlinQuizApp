package com.example.kotlinquizapp

import android.content.ContentValues.TAG
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlin.math.log

class ViewPagerAdapter(var data: List<Question> , var level: String ): RecyclerView.Adapter<Pager2ViewHolder>() {

    var firebaseFirestore: FirebaseFirestore =FirebaseFirestore.getInstance()
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    var firebaseUserId: String = auth.currentUser!!.uid
    private lateinit var prevScore: String
    private lateinit var totalScore: String
    private lateinit var currentLevel : String

    var score = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
                    Log.e("adapteeer","user id $firebaseUserId")


        getScore() // prev score
        getScoreFromFirebase() // total
        getCurrentLevelFromFirebase()

        return Pager2ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        var quizQuestion = data[position]

        holder.question.text = quizQuestion.question
        holder.tvLevel.text = level
        holder.tvScore.text = score.toString()

        holder.submitBtn.visibility = View.GONE


        if ( position == getItemCount()-1 ){
            holder.submitBtn.visibility = View.VISIBLE



            holder.submitBtn.setOnClickListener {

                //getScore()

                val pass = (score?.toDouble()?.div(getItemCount()))?.times(100)
                Log.e("AdapterPager", "$pass")
                if (pass != null) {
                    if (pass >= 50) {
                        val action = QuestionsFragmentDirections.actionQuestionsFragmentToLevelUpFragment()
                        it.findNavController().navigate(action)
                    } else {
                        val action = QuestionsFragmentDirections.actionQuestionsFragmentToTryAgainFragment()
                        it.findNavController().navigate(action)
                    }
                }

                updateScore()
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

    fun getScore(){

        firebaseFirestore.collection("users").document(firebaseUserId)
            .collection("scoreLevel")
            .document("Levels").addSnapshotListener(object: EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null ) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )
                    } else {

                        value!!.apply {
                           prevScore =  value.get("$level").toString()
                            Log.e(TAG, "onEvent: $prevScore $level", )
                        }

                    }
                }

            })
    }

    fun updateScore() {

        getScoreFromFirebase()
        getCurrentLevelFromFirebase()

        Log.e(TAG, "getScoreFromFirebase: $totalScore", )
//        firebaseFirestore.collection("users").document(firebaseUserId)
//            .update("score", "6" )

        if (prevScore.toInt() < score) {
            if ( prevScore.toInt() == 0 ) {
                firebaseFirestore.collection("users").document(firebaseUserId)
                    .collection("scoreLevel").document("Levels")
                    .update("$level", score)
                score = totalScore.toInt() + score
                firebaseFirestore.collection("users").document(firebaseUserId)
                    .update("score", score.toString() )

                Log.e(TAG, "updateScore: $score", )

            } else {
                firebaseFirestore.collection("users").document(firebaseUserId)
                    .collection("scoreLevel").document("Levels")
                    .update("$level", score)
                score = totalScore.toInt() + score - prevScore.toInt()
                firebaseFirestore.collection("users").document(firebaseUserId)
                    .update("score", score.toString() )

                Log.e(TAG, "updateScore: $score $totalScore", )
                //Log.e(TAG, "total: $totalScore", )
            }



        }

        if ( currentLevel < level ) {
            firebaseFirestore.collection("users").document(firebaseUserId)
                .update("currentLevel", level)

        }
    }

    fun checkAnswers ( btn:Button,listOfbtn: List<Button>, answer: String ){

        if (btn.text == answer) {
            btn.setBackgroundColor(Color.GREEN)
            Log.e("score","$score")
            score = score?.plus(1)
        } else {
            btn.setBackgroundColor(Color.RED)
        }
        listOfbtn.forEach {
            it.isEnabled = false
        }

    }

    fun getScoreFromFirebase() {

        firebaseFirestore.collection("users").document(firebaseUserId)
            .addSnapshotListener(object: EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null ) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )
                    } else {
                        value!!.apply {
                            totalScore = value.get("score").toString()

                        }
                    }
                }
            })

    }

    fun getCurrentLevelFromFirebase(){

        firebaseFirestore.collection("users").document(firebaseUserId)
            .addSnapshotListener(object: EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )
                    } else {
                        value!!.apply {
                            currentLevel = value.get("currentLevel").toString()
                            Log.e("TAG", "onEvent: $currentLevel" )
                        }
                    }
                }
            })

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