package fr.lotfirais.eatitup

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import fr.lotfirais.eatitup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideKeyBoardOnTouchOn(binding.root)

    }

    /**
     * Hides keyboard on touch
     * @param v to hide keyboard onclick on
     */
    private fun hideKeyBoardOnTouchOn(v: View) {
        v.setOnTouchListener { view: View, _ ->
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            v.requestFocus()
            v.performClick()
            false
        }
    }
}