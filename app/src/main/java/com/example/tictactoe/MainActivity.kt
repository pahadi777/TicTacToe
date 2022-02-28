package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.appimage.translationY = -1000f
        binding.appname.translationX = 1000f
        binding.offlinematch.translationY = 1000f
        binding.robotmatch.translationY = 1000f

        binding.appimage.animate().translationY(0f).duration = 1000
        binding.appname.animate().translationX(0f).duration = 1000
        binding.offlinematch.animate().translationY(0f).duration = 1000
        binding.robotmatch.animate().translationY(0f).duration = 1000

        binding.offlinematch.setOnClickListener { view ->
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("gametype",1)
            startActivity(intent)
        }

        binding.robotmatch.setOnClickListener { view ->
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("gametype",2)
            startActivity(intent)
        }
    }
}