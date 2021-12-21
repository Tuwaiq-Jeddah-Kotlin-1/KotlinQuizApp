package com.example.kotlinquizapp.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
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
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.kotlinquizapp.MainActivity

class SettingsFragment : Fragment() {

    private lateinit var share: Button
    private lateinit var language: Button
    private lateinit var mode: Button


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
        mode = view.findViewById(R.id.btnDarkMode)
        loadLocale()

        val url = "Application Link"

        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(intent, "Share using"))
        }

        language.setOnClickListener {
            showLangDialog()
            val activity = view.context as AppCompatActivity
            (activity as MainActivity).reload()

        }

        mode.setOnClickListener {
            showModeDialog()
        }
    }

    fun showLangDialog() {
        val listLang = arrayOf("عربي", "English")

        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setTitle("Choose Language")
        mBuilder.setPositiveButton("ok") { _,_ ->
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


        val editor = requireContext().getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun loadLocale(){
        val sharedPreferences = requireContext().getSharedPreferences("Settings", MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocale(language!!)
    }


    // Look for something better
    @SuppressLint("WrongConstant")
    fun showModeDialog(){
        val listMode = arrayOf("Dark Mode","Light Mode")

        val mBuilder = AlertDialog.Builder(context)
        mBuilder.setTitle("Choose Mode")
        mBuilder.setSingleChoiceItems(listMode,-1){ dialog, which ->
            if ( which == 0) {
               AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
               AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun refreshCurrentFragment(){
        val id = findNavController().currentDestination?.id
        findNavController().navigateUp()
        findNavController().navigate(id!!)

        Log.e("Access","$id")
    }

}
