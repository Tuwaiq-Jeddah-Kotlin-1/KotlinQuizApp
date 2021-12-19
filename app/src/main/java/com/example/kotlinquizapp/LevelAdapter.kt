package com.example.kotlinquizapp

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

class LevelAdapter(var data: List<Quiz>): RecyclerView.Adapter<LevelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false)
        return LevelViewHolder(v)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        var quizlevel = data[position]

        holder.tvLevel.text = quizlevel.level.toString()

        holder.itemView.setOnClickListener {
            val action = MainMenuFragmentDirections.actionMainMenuFragmentToStartLevelFragment(quizlevel)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
class LevelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val tvLevel: TextView = itemView.findViewById(R.id.level)

    }

