package com.example.kotlinquizapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileFragment : Fragment() {

    private lateinit var newUsername: EditText
    private lateinit var saveChanges: Button
    private lateinit var deleteAccount: Button
    var firebaseFirestore: FirebaseFirestore =FirebaseFirestore.getInstance()
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    var firebaseUserId: String = auth.currentUser!!.uid
    private lateinit var firebaseuser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newUsername = view.findViewById(R.id.etUsername)
        saveChanges = view.findViewById(R.id.btnSaveChanges)
        deleteAccount = view.findViewById(R.id.btnDeleteAccount)

        firebaseuser = auth.getCurrentUser()!!

        saveChanges.setOnClickListener {
            saveChanges()
        }

        deleteAccount.setOnClickListener {
            showDeleteDialog()
        }
    }
    private fun showDeleteDialog(){
        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setTitle(getString(R.string.deleteAccount))
        mBuilder.setMessage(getString(R.string.areYouSure))
        mBuilder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            firebaseuser.delete()

           firebaseFirestore.collection("users").document(firebaseuser.uid).delete()

            val action = EditProfileFragmentDirections.actionEditProfileFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        mBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ ->

        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun saveChanges(){

        if(newUsername.text.toString()!= ""){
            firebaseFirestore.collection("users").document(firebaseUserId)
                .update("user_name",newUsername.text.toString())
            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
            findNavController().navigate(action)

        } else {
            Toast.makeText(context, getString(R.string.enter_your_new_username), Toast.LENGTH_LONG).show()
        }
    }
}