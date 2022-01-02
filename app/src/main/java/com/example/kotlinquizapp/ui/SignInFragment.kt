package com.example.kotlinquizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore


class SignInFragment : Fragment() {

    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var signIn: Button
    private lateinit var createAccount: Button
    private lateinit var forgetPassword: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.etUsername)
        pass = view.findViewById(R.id.etPassword)
        signIn = view.findViewById(R.id.btnSignIn)
        createAccount = view.findViewById(R.id.btnCreateAccount)
        forgetPassword = view.findViewById(R.id.tvForgetPassword)




        val currentUser = auth.currentUser
        if(currentUser != null ){
            val action = SignInFragmentDirections.actionSignInFragmentToMainMenuFragment()
            findNavController().navigate(action)
        }

        createAccount.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }


        signIn.setOnClickListener {
            if (checkEmpty(arrayListOf(email, pass))) {

                auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            //val firebaseUser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(
                                context,
                                "Signed in Successfully",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            val action =
                                SignInFragmentDirections.actionSignInFragmentToMainMenuFragment()
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

        forgetPassword.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment()
            findNavController().navigate(action)
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
}




