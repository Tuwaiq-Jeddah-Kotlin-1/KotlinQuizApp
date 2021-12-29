package com.example.kotlinquizapp.ui

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class EditProfileFragment : Fragment() {

    private lateinit var choosePhoto: Button
    private lateinit var profilePhoto: ImageView
    private lateinit var newUsername: EditText
    private lateinit var saveChanges: Button
    private lateinit var deleteAccount: Button
    lateinit var filePath: Uri
    var firebaseFirestore: FirebaseFirestore =FirebaseFirestore.getInstance()
    lateinit var ref: DatabaseReference
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

        choosePhoto = view.findViewById(R.id.btnChoosePhoto)
        profilePhoto = view.findViewById(R.id.ivProfilePhoto)
        newUsername = view.findViewById(R.id.etUsername)
        saveChanges = view.findViewById(R.id.btnSaveChanges)
        deleteAccount = view.findViewById(R.id.btnDeleteAccount)

        firebaseuser = auth.getCurrentUser()!!



        //update username
        val new = newUsername.text.toString()


        choosePhoto.setOnClickListener {
            choosePhoto()
        }

        saveChanges.setOnClickListener {
            uploadPhoto()




            if(newUsername.text.toString()!= ""){
                firebaseFirestore.collection("users").document(firebaseUserId)
                    .update("user_name",newUsername.text.toString())
            }

            val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        //delete account

        deleteAccount.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun choosePhoto(){
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,getString(R.string.chosePic)),111)
    }

    private fun uploadPhoto(){
        if(filePath != null){
            var pd = ProgressDialog(context)
            pd.setTitle("Saving Profile Image")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("profilepicture/$firebaseUserId.jpg")
            imageRef.putFile(filePath)
                .addOnSuccessListener { p0->
                    firebaseFirestore.collection("users").document(firebaseUserId)
                        .update("profile_image","profilepicture/$firebaseUserId.jpg")
                    pd.dismiss()
                    Toast.makeText(context,"Profile Image uploaded", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {  p0->
                    pd.dismiss()
                    Toast.makeText(context,p0.message,Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun showDeleteDialog(){
        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setTitle("Delete Account")
        mBuilder.setMessage("Are you sure you want to delete your account?")
        mBuilder.setPositiveButton("Yes") {_,_ ->
            firebaseuser.delete()

           firebaseFirestore.collection("users").document(firebaseuser.uid).delete()

            val action = EditProfileFragmentDirections.actionEditProfileFragmentToSignUpFragment()
            findNavController().navigate(action)


        }

        mBuilder.setNegativeButton("Cancel") { _,_ ->

        }
        val mDialog = mBuilder.create()
        mDialog.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==111 && resultCode == RESULT_OK && data != null) {
            filePath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,filePath)
            profilePhoto.setImageBitmap(bitmap)

        }
    }
}