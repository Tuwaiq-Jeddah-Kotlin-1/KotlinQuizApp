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
import com.example.kotlinquizapp.Data.ScoreLevel
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
    lateinit var ref: DatabaseReference
    private lateinit var auth: FirebaseAuth

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

        firebaseFirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        backToSignIn.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        signUp.setOnClickListener {
         signUP()
        }


    }

    fun signUP(){
        if (checkEmpty(arrayListOf(username, email, pass))) {
            if (confirm.text.toString() != pass.text.toString()) {
                confirm.error = getString(R.string.mismatch)
            } else {
                auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {


                            ref = FirebaseDatabase.getInstance().reference.child("Users")
                                .child(auth.currentUser!!.uid)

                            val user = User(
                                auth.currentUser!!.uid,
                                username.text.toString(),
                                email.text.toString()
                            )
                            val scoreLevel = ScoreLevel()

                            firebaseFirestore.collection("users")
                                .document(auth.currentUser!!.uid)
                                .set(user)
                            firebaseFirestore.collection("users")
                                .document(auth.currentUser!!.uid)
                                .collection("scoreLevel").document("Levels")
                                .set(scoreLevel)

                                .addOnSuccessListener {
                                    Log.d("TAG", "DocumentSnapshot successfully written!")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TAG", "Error writing document", e)
                                }


                            Toast.makeText(
                                context,
                                getString(R.string.new_account),
                                Toast.LENGTH_LONG
                            )
                                .show()

                            val action =
                                SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                            findNavController().navigate(action)

                        } else {
                            Toast.makeText(
                                context,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
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

    val EMAIL_PATTREN = "[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+"
    fun checkEmail(email: String): Boolean {
        if (email.matches(EMAIL_PATTREN.toRegex()))
            return true
        return false
    }
}

