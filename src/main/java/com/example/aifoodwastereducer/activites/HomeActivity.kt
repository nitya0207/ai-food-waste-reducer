package com.example.aifoodwastereducer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aifoodwastereducer.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val pantryButton = findViewById<Button>(R.id.btnPantry)

        pantryButton.setOnClickListener {
            startActivity(Intent(this, PantryActivity::class.java))
        }
    }
}