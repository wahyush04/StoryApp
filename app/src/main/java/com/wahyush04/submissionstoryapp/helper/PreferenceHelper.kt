package com.wahyush04.submissionstoryapp.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceHelper(context: Context) {
    private val PREF = "loginData"
    private val pref : SharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    val editor : SharedPreferences.Editor = pref.edit()
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun put(name: String, token : String){
        editor.apply{
            putString(Constant.NAME, name)
            putString(Constant.TOKEN, token)
        }
    }

    fun putLogin(key: String, isLogin : Boolean){
        editor.putBoolean(key, isLogin)
            .apply()
    }

    fun getIsLogin(key: String) : Boolean{
        return pref.getBoolean(key, false)
    }

    fun getToken(key: String) : String? {
        return  pref.getString(key, null)
    }

    fun clear(){
        editor.clear().apply()
    }


}