package com.example.kotlinquizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlinquizapp.R

class StartLevelFragment : Fragment() {

    private lateinit var startBtn: Button
    private lateinit var backToMain: Button
    private lateinit var levelNo: TextView
    val arg: StartLevelFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startBtn = view.findViewById(R.id.btnStart)
        backToMain = view.findViewById(R.id.btnMainMenu)
        levelNo = view.findViewById(R.id.tvStartLevel)

        levelNo.text = arg.levelArg.level.toString()

        startBtn.setOnClickListener {

            val action = StartLevelFragmentDirections.actionStartLevelFragmentToQuestionsFragment(
                arg.levelArg,
                arg.levelArg.level.toString()
            )
            findNavController().navigate(action)
        }

        backToMain.setOnClickListener {

            val action = StartLevelFragmentDirections.actionStartLevelFragmentToMainMenuFragment()
            findNavController().navigate(action)

        }
    }
}