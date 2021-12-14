package com.example.kotlinquizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizapp.Network.Quiz

class LevelAdapter(var data: List<Quiz>): RecyclerView.Adapter<LevelViewHolder>() {

    private val levels = listOf("Level 1", "Level 2", "Level 3", "Level 4", "Level 5")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false)
        return LevelViewHolder(v)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        var quizlevel = data[position]

        holder.tvLevel.text = quizlevel.level.toString()
    }

    override fun getItemCount(): Int {
        return levels.size
    }

}
class LevelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val tvLevel: TextView = itemView.findViewById(R.id.level)



}