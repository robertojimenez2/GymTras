package com.gymtras.app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gymtras.app.databinding.ActivityMainBinding
import com.gymtras.app.ui.ExercisesCatalogFragment
import com.gymtras.app.ui.HomeDashboardFragment
import com.gymtras.app.ui.RoutineEditorFragment
import com.gymtras.app.ui.WorkoutLogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupNavigationDrawer()
        setupBackPressHandler()

        if (savedInstanceState == null) {
            navigateToMenuItem(R.id.nav_home)
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.setPadding(0, systemBars.top, 0, 0)
            binding.navView.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigationDrawer() {
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            navigateToMenuItem(menuItem.itemId)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    fun navigateToMenuItem(itemId: Int) {
        binding.navView.setCheckedItem(itemId)
        when (itemId) {
            R.id.nav_home -> {
                val homeFragment = HomeDashboardFragment().apply {
                    onNavigateToPage = { targetId -> navigateToMenuItem(targetId) }
                }
                loadFragment(homeFragment)
            }
            R.id.nav_routines -> {
                loadFragment(RoutineEditorFragment())
            }
            R.id.nav_history -> {
                loadFragment(WorkoutLogFragment())
            }
            R.id.nav_exercises -> {
                loadFragment(ExercisesCatalogFragment())
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
