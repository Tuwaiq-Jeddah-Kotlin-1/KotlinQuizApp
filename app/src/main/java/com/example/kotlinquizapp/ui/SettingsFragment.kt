package com.example.kotlinquizapp.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat.recreate
import com.example.kotlinquizapp.R
import java.util.*
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.MainActivity


class SettingsFragment : Fragment() {

    private lateinit var share: Button
    private lateinit var language: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        share = view.findViewById(R.id.btnShare)
        language = view.findViewById(R.id.btnLanguage)



        val url = "Application Link"

        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(intent, "Share using"))
        }

        language.setOnClickListener {
            showLangDialog()
        }

    }

    fun showLangDialog() {
        val listLang = arrayOf("عربي", "English")
        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setTitle(getString(R.string.Choose_language))
        mBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
            refreshCurrentFragment()
        }
        mBuilder.setSingleChoiceItems(listLang, -1) { dialog, which ->

            if (which == 0) {
                setLocale("ar")
            } else {
                setLocale("en")
            }
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    fun setLocale(lang: String){

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        val myPref = requireContext().getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = myPref.edit()

        editor.putString("MyLang",lang)
        editor.commit()

    }

    fun loadLocale(){
        var sharedPreferences = requireContext().getSharedPreferences("myPref", MODE_PRIVATE)
        var language = sharedPreferences.getString("MyLang", "")
        setLocale(language!!)

    }



    private fun refreshCurrentFragment(){
        val id = findNavController().currentDestination?.id
        findNavController().navigateUp()
        findNavController().navigate(id!!)

        Log.e("Access","$id")
    }



}

