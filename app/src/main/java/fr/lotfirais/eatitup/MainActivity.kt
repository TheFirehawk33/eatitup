package fr.lotfirais.eatitup

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import fr.lotfirais.eatitup.databinding.ActivityMainBinding
import fr.lotfirais.eatitup.ui.fragments.FavoriteFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
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


