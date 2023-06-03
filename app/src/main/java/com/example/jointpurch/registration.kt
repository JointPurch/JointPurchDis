package com.example.jointpurch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jointpurch.data.User

class registration : AppCompatActivity() {
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()


        Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()

        emailEditText = findViewById(R.id.EditEmail)
        passwordEditText = findViewById(R.id.EditPassword)
        loginButton = findViewById(R.id.btn_enter)
        registerButton = findViewById(R.id.btn_register)

        val loginIntent = Intent(this, authorization::class.java)
        val mainIntent = Intent(this, MainActivity::class.java)

        loginButton.setOnClickListener {
            startActivity(loginIntent)
        }

        registerButton.setOnClickListener {
            if (emailEditText.text.isEmpty()){
                emailEditText.error = "Заполните поле"
                return@setOnClickListener
            }
            if (passwordEditText.text.isEmpty()){
                passwordEditText.error = "Заполните поле"
                return@setOnClickListener
            }

            val newUser = User(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )

            val restApi = RestApi(this)
            restApi.register(newUser.login, newUser.passwordHash!!)

            SharedPreferenceManager.saveUser(applicationContext, newUser)

            startActivity(mainIntent)
        }
    }
}