package com.example.kotlinquizapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kotlinquizapp.Data.User

class SignUpFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var confirm: EditText
    private lateinit var username: EditText
    private lateinit var signUp: Button
    private lateinit var backToSignIn: ImageView
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var firebaseUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.etEmailSignUp)
        pass = view.findViewById(R.id.etPasswordSignUp)
        confirm = view.findViewById(R.id.etConfirmPassword)
        username = view.findViewById(R.id.etUsernameSignUp)
        signUp = view.findViewById(R.id.btnSignUp)
        backToSignIn = view.findViewById(R.id.ivBackToSignIn)
        auth = FirebaseAuth.getInstance()

        backToSignIn.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        signUp.setOnClickListener {
            if (checkEmpty(arrayListOf(username, email, pass))) {
                if (confirm.text.toString() != pass.text.toString()) {
                    confirm.error = "Password mismatch"
                } else {
                    auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                firebaseUserId = auth.currentUser!!.uid!!
                                ref = FirebaseDatabase.getInstance().reference.child("Users")
                                    .child(firebaseUserId)
                                val user = User(firebaseUserId,username.text.toString(),email.text.toString())
                                firebaseFirestore = FirebaseFirestore.getInstance()
                                firebaseFirestore.collection("users").document(firebaseUserId)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Log.d("TAG", "DocumentSnapshot successfully written!")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("TAG", "Error writing document", e)
                                    }


                                Toast.makeText(context,
                                    "You've created new account Successfully",
                                    Toast.LENGTH_LONG)
                                    .show()

                                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                findNavController().navigate(action)

                            } else {
                                Toast.makeText(context,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_LONG)
                                    .show()
                            } }
                }
            }
        }


    }


    }

    fun checkEmpty(arrayListOf: ArrayList<EditText>): Boolean {
            var returnValue = false
            for (i in arrayListOf) {
                if (i.text.toString() == "") {
                    i.error = "must be filled"
                    returnValue = false
                } else {
                    returnValue = true
                }
            }
            return returnValue
    }

