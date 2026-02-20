package uz.group1.maxwayapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.group1.maxwayapp.presentation.screens.main.MainScreen
import uz.group1.maxwayapp.presentation.screens.test.TestScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, v.paddingBottom)
            insets
        }

//        supportFragmentManager.beginTransaction()
//            .add(R.id.main,TestScreen())
//            .commit()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.itemHome -> {
                    replaceFragment(
                        MainScreen()
                    )
                    true
                }

                R.id.itemCart -> {
                    replaceFragment(TestScreen())
                    true
                }

                R.id.itemProfile -> {
                    replaceFragment(TestScreen())
                    true
                }

                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, fragment)
            .commit()
    }

}




/*

    My Tasks:

    1 - TASK
    MainScreen: bootom navigation + viewpager2

 */