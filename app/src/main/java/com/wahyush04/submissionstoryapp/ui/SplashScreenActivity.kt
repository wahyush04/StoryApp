package com.wahyush04.submissionstoryapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.wahyush04.submissionstoryapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var img : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        img = findViewById(R.id.iv_logo)

        img.alpha = 0f
        img.animate().setDuration(2000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(img, "iv_logo")).toBundle()
            startActivity(intent, bundle)
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}