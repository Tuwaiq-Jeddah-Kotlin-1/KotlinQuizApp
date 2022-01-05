package com.example.kotlinquizapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException


class LevelUpFragment : Fragment() {

    private lateinit var mainMenuBtn: Button
    private lateinit var nextLevelBtn: Button

    private lateinit var yourScore: TextView


//    var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//    val auth : FirebaseAuth = FirebaseAuth.getInstance()
//    var firebaseUserId: String = auth.currentUser!!.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainMenuBtn = view.findViewById(R.id.btnMainMenu)
       // yourScore = view.findViewById(R.id.yourScore)

//        firebaseFirestore.collection("users").document(firebaseUserId)
//            .collection("scoreLevel")
//            .document("Levels").addSnapshotListener(object: EventListener<DocumentSnapshot> {
//                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
//                    if (error != null) {
//                        Log.e(
//                            "TAG",
//                            "Firestore error in retrieving data" + error.message.toString()
//                        )
//                    } else {
//                        yourScore.text = value!!.get("").toString()
//                    }
//                }
//            })



        mainMenuBtn.setOnClickListener {
            val action = LevelUpFragmentDirections.actionLevelUpFragmentToMainMenuFragment()
            findNavController().navigate(action)
        }
    }

}