package com.example.jointpurch

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


//        val icon: ImageView =findViewById(R.id.imageView4)
//
//        val animator = ValueAnimator.ofFloat(0f, 100f)
//        animator.duration = 1000
//        animator.start()
//
//        animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
//            override fun onAnimationUpdate(animation: ValueAnimator) {
//                val animatedValue = animation.animatedValue as Float
//                icon.translationX = animatedValue
//            }
//        })

    }
}