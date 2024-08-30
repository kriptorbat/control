package com.example.control.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.example.control.R
import com.example.control.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFullScreen()
        startAnimation()
        setDelay(3000)

    }
    //FullScreen
    private fun setFullScreen(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
    //Запуск анимации
    private fun startAnimation(){
        binding.textView5.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_in))
        binding.imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_recycler_view))
    }
    //Задержка на несколько секунд
    private fun setDelay(s: Long){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, s)
    }
}