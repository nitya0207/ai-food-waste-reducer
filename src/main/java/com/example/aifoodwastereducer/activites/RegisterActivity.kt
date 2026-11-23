package com.example.aifoodwastereducer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aifoodwastereducer.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        val registerButton = findViewById<Button>(R.id.btnRegister)
        val loginText = findViewById<TextView>(R.id.tvLogin)

        registerButton.setOnClickListener {

            val userName = name.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString()
            val confirm = confirmPassword.text.toString()

            if (userName.isEmpty() ||
                userEmail.isEmpty() ||
                userPassword.isEmpty() ||
                confirm.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please fill all details",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (userPassword != confirm) {

                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                auth.createUserWithEmailAndPassword(
                    userEmail,
                    userPassword
                )
                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Account created successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this, LoginActivity::class.java)
                        )

                        finish()
                    }

                    .addOnFailureListener { error ->

                        Toast.makeText(
                            this,
                            error.message ?: "Registration failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

        loginText.setOnClickListener {
            finish()
        }
    }
}