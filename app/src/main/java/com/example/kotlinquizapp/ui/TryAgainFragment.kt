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


class TryAgainFragment : Fragment() {

    private lateinit var mainMenuBtn: Button
    private lateinit var tryAgainBtn: Button

    private lateinit var tvYourScore: TextView

//    var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//    val auth : FirebaseAuth = FirebaseAuth.getInstance()
//    var firebaseUserId: String = auth.currentUser!!.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_try_again, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainMenuBtn = view.findViewById(R.id.btnMainMenu)
        tvYourScore = view.findViewById(R.id.tvYourScore)

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
//                        tvYourScore.text = value!!.get("").toString()
//                    }
//                }
//            })

        mainMenuBtn.setOnClickListener {
            val action = TryAgainFragmentDirections.actionTryAgainFragmentToMainMenuFragment()
            findNavController().navigate(action)
        }
    }


}