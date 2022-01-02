package com.example.kotlinquizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth


class ForgetPasswordFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEmail = view.findViewById(R.id.etEmailAddress)
        btnSubmit = view.findViewById(R.id.btnResetPassword)

        btnSubmit.setOnClickListener {
            val email = etEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(context,"Please enter your email address",Toast.LENGTH_LONG).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(context, "Email sent successfully",Toast.LENGTH_LONG).show()

                            val action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToSignInFragment()
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }

                    }
            }
        }
    }


}