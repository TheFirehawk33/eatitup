package fr.lotfirais.eatitup.utils

import android.content.Context
import android.widget.Toast

object Common {
    fun onFailure(context: Context, t: Throwable) {
        Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
    }
}