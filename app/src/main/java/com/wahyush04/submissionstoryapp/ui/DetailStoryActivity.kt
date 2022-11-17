package com.wahyush04.submissionstoryapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.wahyush04.submissionstoryapp.databinding.ActivityDetailStoryBinding
import com.wahyush04.submissionstoryapp.helper.Constant

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(Constant.EXTRA_NAME)
        val description = intent.getStringExtra(Constant.EXTRA_DESCRIPTION)
        val avatar = intent.getStringExtra(Constant.EXTRA_AVATAR)

        binding.apply {
            tvName.text = name
            tvDescription.text = description
            Glide.with(this@DetailStoryActivity)
                .load(avatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivStory)
        }
    }



}