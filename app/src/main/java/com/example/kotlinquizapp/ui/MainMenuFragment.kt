package com.example.kotlinquizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizapp.Data.MainVM
import com.example.kotlinquizapp.LevelAdapter
import com.example.kotlinquizapp.R


class MainMenuFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvLevel)
        recyclerView.layoutManager = LinearLayoutManager(context)

        var vm  = ViewModelProvider(this).get(MainVM::class.java)

        vm.fetchQuiz().observe(viewLifecycleOwner, {
            //
            recyclerView.adapter = LevelAdapter(it.quiz)

        } )



    }
}