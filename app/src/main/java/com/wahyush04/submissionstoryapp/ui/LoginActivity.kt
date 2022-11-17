package com.wahyush04.submissionstoryapp.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wahyush04.submissionstoryapp.customview.EmailEditText
import com.wahyush04.submissionstoryapp.customview.PasswordEditText
import com.wahyush04.submissionstoryapp.data.LoginResponse
import com.wahyush04.submissionstoryapp.databinding.ActivityLoginBinding
import com.wahyush04.submissionstoryapp.helper.Constant
import com.wahyush04.submissionstoryapp.helper.PreferenceHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: PreferenceHelper
    private lateinit var EdtPassword : PasswordEditText
    private lateinit var EdtEmail : EmailEditText
    private lateinit var btnLogin : Button
    private val mainViewModel: MainViewModel by viewModels {
    ViewModelFactory(this)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sharedPreferences = PreferenceHelper(this)
        playAnimation()

        EdtPassword = binding.edtPassword
        EdtEmail = binding.edtEmail
        btnLogin = binding.btnLogin

        EdtEmail.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButton()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        EdtPassword.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButton()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.btnLogin.setOnClickListener {
            showLoading(true)
            login()
        }

        binding.tvMakeaccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login(){
        showLoading(true)
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val result = mainViewModel.login(email, password)
        result.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    response.body()?.loginResult?.let { sharedPreferences.put(it.name, it.token) }
                    sharedPreferences.putLogin(Constant.IS_LOGIN, true)
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    showLoading(false)
                    finish()
                    Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    Toast.makeText(applicationContext, jObjError.getString("message"), Toast.LENGTH_SHORT).show()
                    Log.e("Failed", "Gagal Register")
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun playAnimation() {
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailedtlayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordedtlayout, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val tvNoAccount = ObjectAnimator.ofFloat(binding.tvNoaccount, View.ALPHA, 1f).setDuration(500)
        val makeAccount = ObjectAnimator.ofFloat(binding.tvMakeaccount, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(login, email, password, btnLogin, tvNoAccount, makeAccount)
            start()
        }
    }
    private fun setLoginButton() {
        val email = EdtEmail.text.toString()
        if(EdtPassword.length() >= 6 &&  Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            btnLogin.isEnabled = true
        }
    }
}