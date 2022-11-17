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
import com.wahyush04.submissionstoryapp.data.RegisterResponse
import com.wahyush04.submissionstoryapp.databinding.ActivityRegisterBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var edtPassword : PasswordEditText
    private lateinit var edtEmail : EmailEditText
    private lateinit var btnRegister : Button
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        playAnimation()
        edtPassword = binding.edtPassword
        edtEmail = binding.edtEmail
        btnRegister = binding.btnRegister

        edtEmail.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButton()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        edtPassword.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButton()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.btnRegister.setOnClickListener {
            showLoading(true)
            register()
        }

        binding.tvGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun register(){
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        val result = mainViewModel.register(name, email, password)
        result.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    showLoading(false)
                    Toast.makeText(applicationContext, "Berhasil membuat akun", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val error = jObjError.getBoolean("error")
                    val message = jObjError.getString("message")
                    showLoading(false)
                    Log.d("Error","$error")
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Failed", "Retrofit Gagal")
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
        val login = ObjectAnimator.ofFloat(binding.textregister, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameedtlayout, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailedtlayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordedtlayout, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val tvHaveAccount = ObjectAnimator.ofFloat(binding.tvHaveAccount, View.ALPHA, 1f).setDuration(500)
        val makeAccount = ObjectAnimator.ofFloat(binding.tvGoLogin, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(login, name, email, password, btnRegister, tvHaveAccount, makeAccount)
            start()
        }
    }
    private fun setLoginButton() {
        val email = edtEmail.text.toString()
        if(edtPassword.length() >= 6 &&  Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            btnRegister.isEnabled = true
        }
    }
}