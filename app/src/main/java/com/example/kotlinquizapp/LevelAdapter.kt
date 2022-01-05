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

class LevelAdapter(var data: List<Quiz>, var nextLevel: String) :
    RecyclerView.Adapter<LevelViewHolder>() {


    // var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    //var firebaseUserId: String = auth.currentUser!!.uid


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false)


        return LevelViewHolder(v)
    }


    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {

        var quizlevel = data[position]


        holder.tvLevel.text = ("Level " + quizlevel.level.toString())

        if (quizlevel.level.toInt() <= nextLevel.toInt()) {
            holder.itemView.isEnabled = true
        } else {
            holder.itemView.isEnabled = false

            holder.itemView.background.setTint(Color.LTGRAY)
        }


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





