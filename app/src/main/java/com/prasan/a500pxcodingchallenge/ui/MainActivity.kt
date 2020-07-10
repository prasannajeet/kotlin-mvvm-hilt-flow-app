package com.prasan.a500pxcodingchallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prasan.a500pxcodingchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}