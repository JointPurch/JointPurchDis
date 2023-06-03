package com.example.jointpurch

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jointpurch.data.Room
import com.google.gson.GsonBuilder
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        if(SharedPreferenceManager.getUser(applicationContext) == null){
            val loginIntent = Intent(this, authorization::class.java)
            startActivity(loginIntent)
        }

        val icon: ImageView =findViewById(R.id.imageView4)

        val animator = ValueAnimator.ofFloat(0f, 100f)
        animator.duration = 1000
        animator.start()

        animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                val animatedValue = animation.animatedValue as Float
                icon.translationX = animatedValue
            }
        })


        val testButton: Button = findViewById(R.id.room_btn)
        val testTextView: TextView = findViewById(R.id.test_textview)

        testButton.setOnClickListener {
            val restApi = RestApi(this)
            val myRooms: List<Room> = restApi.getMyRooms()
            val gson = GsonBuilder().setPrettyPrinting().create()
            testTextView.text = gson.toJson(myRooms)
        }
    }
}