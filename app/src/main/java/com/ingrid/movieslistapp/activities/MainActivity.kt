package com.ingrid.movieslistapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.ingrid.movieslistapp.R
import com.ingrid.movieslistapp.databinding.ActivityMainBinding
import com.ingrid.movieslistapp.fragment.MovieDetailsFragment
import com.ingrid.movieslistapp.fragment.MoviesListFragment
import com.ingrid.movieslistapp.viewmodels.MoviesViewModel
import com.ingrid.movieslistapp.viewmodels.Status

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initStatus()
        initViewModel()

        changeFragmentWithoutBackStack(MoviesListFragment())
    }

    private fun initStatus() {
        viewModel.getStatus().observe(this) {
            when (it) {
                Status.LOADING -> {
                    Toast.makeText(this, "Aguarde... Carregando lista!", Toast.LENGTH_LONG).show()
                }
                Status.FAILURE -> {
                    Toast.makeText(this, "Ocorreu erro ao carregar dados.", Toast.LENGTH_LONG)
                        .show()
                }
                else -> {
                    Toast.makeText(this, "Dados carregados!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.getSelectedMovie().observe(this) {
            changeFragmentWithBackStack(MovieDetailsFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_search -> {
                startSearchActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun startSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}