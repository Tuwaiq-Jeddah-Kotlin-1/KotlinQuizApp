package com.example.kotlinquizapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinquizapp.Data.MainVM
import com.example.kotlinquizapp.LevelAdapter
import com.example.kotlinquizapp.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException


class MainMenuFragment : Fragment() {

    private lateinit var username: TextView

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

        username = view.findViewById(R.id.tvPlayerName)
        recyclerView = view.findViewById(R.id.rvLevel)
        recyclerView.layoutManager = LinearLayoutManager(context)

        firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseUserId = auth.currentUser!!.uid

        firebaseFirestore.collection("users")
            .document(firebaseUserId)
            .addSnapshotListener(object : EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )
                    } else {
                        username.text = value!!.getString("user_name")
                    }
                }
            })
        var vm = ViewModelProvider(this).get(MainVM::class.java)

        vm.fetchQuiz().observe(viewLifecycleOwner, {
            recyclerView.adapter = LevelAdapter(it.quiz)
        })
    }
}


