package com.example.jointpurch

import android.content.Context
import android.content.SharedPreferences
import com.example.jointpurch.data.User
import java.security.MessageDigest

object SharedPreferenceManager{
    fun saveUser(context: Context, user: User){
        val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val passwordHash: String = MessageDigest.getInstance("SHA-512")
            .digest(user.passwordHash!!.toByteArray())
            .joinToString(separator = "") {
                ((it.toInt() and 0xff) + 0x100)
                    .toString(16)
                    .substring(1)
            }

        editor.putString("login", user.login)
        editor.putString("password", passwordHash)
        editor.apply()
    }

    fun getUser(context: Context): User?{
        val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return try {
            User(
                sharedPreferences.getString("login", null)!!,
                sharedPreferences.getString("password", null)!!
            )
        }catch (e: Exception){
            null
        }
    }

//    fun isUserExists(context: Context): Boolean{
//        val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
//        val id = sharedPreferences.getString("id", null)
//        val login = sharedPreferences.getString("login", null)
//        val password = sharedPreferences.getString("login", null)
//        return id != null && login != null && password != null
//    }
}