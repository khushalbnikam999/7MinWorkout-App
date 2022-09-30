package com.example.a7minworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding :ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flbutton?.setOnClickListener{
            val intent =Intent(this,exerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener {
            // Launching the History Activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}