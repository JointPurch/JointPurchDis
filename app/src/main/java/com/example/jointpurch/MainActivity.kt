package com.example.jointpurch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.jointpurch.data.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(SharedPreferenceManager.getUser(applicationContext) == null){
            val loginIntent = Intent(this, authorization::class.java)
            startActivity(loginIntent)
        }

        val testButton: Button = findViewById(R.id.room_btn)

        testButton.setOnClickListener {
            val restApi = RestApi(this)
            val myRooms: List<Room> = restApi.getMyRooms()
            Toast.makeText(this, myRooms.map { it.name }.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}