package com.prasan.kotlinmvvmhiltflowapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prasan.kotlinmvvmhiltflowapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This activity acts as the host for all fragments in the application
 * @author Prasan
 * @since 1.0
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}