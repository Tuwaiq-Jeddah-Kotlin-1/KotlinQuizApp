package com.example.kotlinquizapp.ui

import android.content.Context.MODE_PRIVATE
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.kotlinquizapp.Data.User
import com.example.kotlinquizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.w3c.dom.Text
import java.io.File


class ProfileFragment : Fragment() {


    private lateinit var signOut: Button
    private lateinit var editProfile: Button
    private lateinit var username: TextView
    private lateinit var profileImage: String

    private lateinit var level: TextView
    private lateinit var score: TextView

    var firebaseFirestore: FirebaseFirestore =FirebaseFirestore.getInstance()
    //lateinit var ref: DatabaseReference
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    var firebaseUserId: String = auth.currentUser!!.uid




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun retrieveData(){
        val myPref = requireActivity().getSharedPreferences("profileinfo",MODE_PRIVATE)
        val _name = myPref.getString("user_name"," ")

        username.setText(_name)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signOut = view.findViewById(R.id.btnSignOut)
        username = view.findViewById(R.id.tvPlayerName)
        editProfile = view.findViewById(R.id.btnEditProfile)
        level = view.findViewById(R.id.tvLevel)
        score = view.findViewById(R.id.tvTotalScore)
        //profileImage = view.findViewById(R.id.ivProfile)
        //retrieveData()

        editProfile.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }

        signOut.setOnClickListener {
            auth.signOut()
            Toast.makeText(context, getString(R.string.SignedOut), Toast.LENGTH_LONG).show()
            val action = ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        firebaseFirestore.collection("users")
            .document(firebaseUserId)
            .addSnapshotListener(object: EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null ) {
                        Log.e(
                            "TAG",
                            "Firestore error in retrieving data" + error.message.toString()
                        )
                    } else {
//                        val user = value!!.toObject(User::class.java)
//                        username.text = user!!.user_name

                        value!!.apply {
                            username.text = value.getString("user_name")
                            level.text = value.get("currentLevel").toString()
                            score.text = value.get("score").toString()
                                // no profile image displayed
                           // view.ivProfile.load(profileImage)


                            //profileImage = value.getString("profile_image").toString()
                            //view.ivProfile.load(profileImage)
                            //tvLevel.text = value.get("currentLevel").toString()
                            //value.get
                            //tvTotalScore.text = value.getString("score")
                      }

                    }
                }

            })


        val imageRef = FirebaseStorage.getInstance().reference.child("profilepictures/$firebaseUserId.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        imageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            view.ivProfile.load(profileImage)

        }

    }
}