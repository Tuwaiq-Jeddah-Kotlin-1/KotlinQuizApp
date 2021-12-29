package com.example.kotlinquizapp

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizapp.Network.Data
import com.example.kotlinquizapp.Network.Quiz
import com.example.kotlinquizapp.ui.MainMenuFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class LevelAdapter(var data: List<Quiz>): RecyclerView.Adapter<LevelViewHolder>() {


    var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    var firebaseUserId: String = auth.currentUser!!.uid
     var nextLevel : String =""
    var xxxx :String =""



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false)

        getNextLevel()
        v.isEnabled =false
        return LevelViewHolder(v)
    }
    fun getNextLevel(){

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
                            nextLevel = value.get("nextLevel").toString()
                            Log.e("TAG", "inner: $nextLevel", )
                            xxxx=nextLevel
                        }
                    }
                }
            })
        Log.e("TAG", "outer: $nextLevel", )
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        getNextLevel()
       //checkLevel(holder)
        var quizlevel = data[position]

        Log.e("TAG", "bind: $xxxx", )
        holder.tvLevel.text = quizlevel.level.toString()

//        if ( quizlevel.level.toInt() <= nextLevel.toInt() ) {
//            holder.itemView.isEnabled =  true
//        }


        if (holder.itemView.isEnabled) {
            holder.itemView.setOnClickListener {

                val action =
                    MainMenuFragmentDirections.actionMainMenuFragmentToStartLevelFragment(quizlevel)
                it.findNavController().navigate(action)

            }
        }
    }

    override fun getItemCount(): Int {
            return data.size
        }

    }

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLevel: TextView = itemView.findViewById(R.id.level)
    }





