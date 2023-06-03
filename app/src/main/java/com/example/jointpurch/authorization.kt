package com.example.jointpurch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jointpurch.data.User

class authorization : AppCompatActivity() {
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        Toast.makeText(this, "Auth", Toast.LENGTH_SHORT).show()

        emailEditText = findViewById(R.id.EditEmail)
        passwordEditText = findViewById(R.id.EditPassword)
        loginButton = findViewById(R.id.btn_enter)
        registerButton = findViewById(R.id.btn_register)

        val registerIntent = Intent(this, registration::class.java)
        val mainIntent = Intent(this, MainActivity::class.java)

        registerButton.setOnClickListener {
            startActivity(registerIntent)
        }

        loginButton.setOnClickListener {
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
            val userExists = restApi.login(newUser)

            if (userExists){
                SharedPreferenceManager.saveUser(this, newUser)
                Toast.makeText(this, "Вы успешно вошли", Toast.LENGTH_LONG).show()
                startActivity(mainIntent)
            }else{
                Toast.makeText(this, "Неверные данные", Toast.LENGTH_LONG).show()
            }
        }
    }
}