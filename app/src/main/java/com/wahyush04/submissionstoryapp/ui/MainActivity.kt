package com.wahyush04.submissionstoryapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.wahyush04.submissionstoryapp.databinding.ActivityMainBinding
import com.wahyush04.submissionstoryapp.helper.Constant
import com.wahyush04.submissionstoryapp.helper.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: PreferenceHelper
    private lateinit var img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        img = binding.ivLogo

        sharedPreferences = PreferenceHelper(this)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(img, "iv_logo")).toBundle()
            startActivity(intent, bundle)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(img, "iv_logo")).toBundle()
            startActivity(intent, bundle)
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferences.getIsLogin(Constant.IS_LOGIN)){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}