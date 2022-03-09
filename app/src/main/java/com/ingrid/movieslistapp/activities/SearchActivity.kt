package com.ingrid.movieslistapp.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.ingrid.movieslistapp.R
import com.ingrid.movieslistapp.databinding.ActivitySearchBinding
import com.ingrid.movieslistapp.fragment.MovieDetailsFragment
import com.ingrid.movieslistapp.fragment.MoviesListFragment
import com.ingrid.movieslistapp.viewmodels.MoviesViewModel

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViewModel()

        changeFragmentWithoutBackStack(MoviesListFragment())
    }

    private fun initViewModel() {
        viewModel.getSelectedMovie().observe(this) {
            changeFragmentWithBackStack(MovieDetailsFragment(), "movieDetails")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setQuery("", false)
            requestFocus()

            queryHint = resources.getString(R.string.search_hint)

            isIconified = false

            //forceRemoveAllBackgrounds(this)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchMovie(query)
                    return false
                }

                override fun onQueryTextChange(text: String): Boolean {
                    searchMovie(text)
                    return false
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchMovie(query: String) {

        val fragment: Fragment? = supportFragmentManager.findFragmentByTag("movieDetails")
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .remove(fragment)
                .add(R.id.fragment_content, MoviesListFragment())
                .commit()
        }

        viewModel.searchMovies(query)
    }

}