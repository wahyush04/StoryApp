package com.wahyush04.submissionstoryapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyush04.submissionstoryapp.R
import com.wahyush04.submissionstoryapp.adapter.LoadingStateAdapter
import com.wahyush04.submissionstoryapp.adapter.StoryListAdapter
import com.wahyush04.submissionstoryapp.data.ListStory
import com.wahyush04.submissionstoryapp.databinding.ActivityHomeBinding
import com.wahyush04.submissionstoryapp.helper.Constant
import com.wahyush04.submissionstoryapp.helper.PreferenceHelper

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences: PreferenceHelper
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = PreferenceHelper(this)
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        getStory()

        binding.fab.setOnClickListener {
            addStory()
        }
    }

    private fun addStory() {
        startActivity(Intent(this, AddStoryActivity::class.java))
    }

    private fun getStory() {
        val adapter = StoryListAdapter(object : StoryListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListStory) {
                Intent(this@HomeActivity, DetailStoryActivity::class.java).also {
                    it.putExtra(Constant.EXTRA_NAME, data.name)
                    it.putExtra(Constant.EXTRA_DESCRIPTION, data.description)
                    it.putExtra(Constant.EXTRA_AVATAR, data.photoUrl)
                    startActivity(it)
                }
            }

        })
        val token: String = sharedPreferences.getToken(Constant.TOKEN).toString()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.getStory(token).observe(this) {
            adapter.submitData(lifecycle, it)
        }
        showLoading(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to Logout")
                    .setPositiveButton("Ya") { _, _ ->
                        logout()
                        Toast.makeText(
                            applicationContext,
                            "Log out Berhasil",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .setNegativeButton("No") { _, _ ->
                    }
                    .show()
            }
            R.id.refresh -> {
                this.recreate()
            }
            R.id.map -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        sharedPreferences.clear()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun showLoading(state: Boolean){
        if (state){
            binding.baseEmpty.root.visibility = View.VISIBLE
        }else{
            binding.baseEmpty.root.visibility = View.GONE
        }
    }

}