package com.example.habittracker.activity


import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.habittracker.R
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.room.UserDB

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var drawerLayout: DrawerLayout? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserDB.getInstance(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navDrawerSetup()
        drawerLayout = binding.drawerLayout
    }

    private fun navDrawerSetup() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            val sharedPreferences = getSharedPreferences("UserName", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")
            updateUsernameInNavDrawerHeader(username ?: "")
            when (menuItem.itemId) {
                R.id.nav_profile -> navController.navigate(R.id.profileFragment)
                R.id.nav_today -> navController.navigate(R.id.homeFragment)
                R.id.nav_Stats -> navController.navigate(R.id.statsFragment)
                R.id.nav_challenge -> navController.navigate(R.id.challengesFragment)
                R.id.nav_settings -> navController.navigate(R.id.settingsFragment)
                R.id.nav_Try_free -> navController.navigate(R.id.subscriptionFragment)
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.loginFragment || destination.id == R.id.signUpFragment) {
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                } else {
                    drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }

            drawerLayout?.closeDrawers()
            true
        }

    }

    fun openDrawer() {
        drawerLayout?.openDrawer(GravityCompat.START)
    }

    private fun updateUsernameInNavDrawerHeader(username: String) {
        val headerView = binding.navView.getHeaderView(0)
        val usernameTextView = headerView.findViewById<TextView>(R.id.usrnm)
        usernameTextView.text = username
    }


}




