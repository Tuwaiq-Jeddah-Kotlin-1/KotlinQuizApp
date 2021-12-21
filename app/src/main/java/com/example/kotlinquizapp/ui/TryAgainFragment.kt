package com.example.kotlinquizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlinquizapp.R
import java.util.logging.Level


class TryAgainFragment : Fragment() {

    private lateinit var tryAgain: Button
    private lateinit var mainMenu: Button

    val arg: TryAgainFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_try_again, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tryAgain = view.findViewById(R.id.btnTryAgain)
        mainMenu = view.findViewById(R.id.btnMainMenu)

        tryAgain.setOnClickListener {
            val action = TryAgainFragmentDirections.actionTryAgainFragmentToStartLevelFragment(arg.levArg)
            it.findNavController().navigate(action)
        }

        mainMenu.setOnClickListener {
            val action = TryAgainFragmentDirections.actionTryAgainFragmentToMainMenuFragment()
            it.findNavController().navigate(action)
        }
    }

}