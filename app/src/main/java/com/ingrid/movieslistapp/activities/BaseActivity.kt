package com.ingrid.movieslistapp.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ingrid.movieslistapp.R

open class BaseActivity : AppCompatActivity() {

    protected fun changeFragmentWithBackStack(fragment: Fragment, tag: String? = null) {
        changeFragment(fragment, true, tag)
    }


    protected fun changeFragmentWithoutBackStack(fragment: Fragment) {
        changeFragment(fragment, false)
    }

    private fun changeFragment(
        fragment: Fragment,
        shouldAddToBackStack: Boolean,
        tag: String? = null
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_content, fragment, tag)
            .apply {
                if (shouldAddToBackStack) {
                    addToBackStack(null)
                }
            }
            .commit()
    }
}