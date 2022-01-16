package com.example.kotlinquizapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var sharedPreferences = this.getSharedPreferences("myPref", MODE_PRIVATE)
        var language = sharedPreferences.getString("MyLang", "")
        language.let {
            setLocale(language!!)
        }


        bottomNav = findViewById(R.id.bottomNav)
        navController = findNavController(R.id.fragmentContainerView)

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signUpFragment -> bottomNav.visibility = View.GONE
                R.id.signInFragment -> bottomNav.visibility = View.GONE
                R.id.questionsFragment -> bottomNav.visibility = View.GONE
                R.id.forgetPasswordFragment -> bottomNav.visibility = View.GONE
                R.id.startLevelFragment -> bottomNav.visibility = View.GONE
                R.id.tryAgainFragment -> bottomNav.visibility = View.GONE
                R.id.levelUpFragment -> bottomNav.visibility = View.GONE
                else -> bottomNav.visibility = View.VISIBLE
            }
        }

        myWorkerManger()

    }

    fun setLocale(lang: String) {

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(
            config,
           this.resources.displayMetrics
        )

        val myPref = this.getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = myPref.edit()

        editor.putString("MyLang", lang)
        editor.commit()
    }

        private fun myWorkerManger() {

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()
        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,15, TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP,myRequest)
    }

    private fun simpleWork() {
        val mRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
        WorkManager.getInstance(this).enqueue(mRequest)
    }

}








