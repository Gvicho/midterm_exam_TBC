package com.example.test7.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test7.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}