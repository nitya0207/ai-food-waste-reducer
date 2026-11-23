package com.example.aifoodwastereducer.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aifoodwastereducer.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class AddFoodActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var expiryDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        db = FirebaseFirestore.getInstance()

        val foodName = findViewById<EditText>(R.id.etFoodName)
        val quantity = findViewById<EditText>(R.id.etQuantity)
        val category = findViewById<EditText>(R.id.etCategory)

        val expiryButton = findViewById<Button>(R.id.btnExpiryDate)
        val saveButton = findViewById<Button>(R.id.btnSaveFood)

        expiryButton.setOnClickListener {

            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->

                    expiryDate = "$day/${month + 1}/$year"
                    expiryButton.text = "Expiry: $expiryDate"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.datePicker.minDate = System.currentTimeMillis()
            datePicker.show()
        }

        saveButton.setOnClickListener {

            val name = foodName.text.toString().trim()
            val qty = quantity.text.toString().trim()
            val cat = category.text.toString().trim()

            if (name.isEmpty() ||
                qty.isEmpty() ||
                cat.isEmpty() ||
                expiryDate.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all details",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val food = hashMapOf(
                    "name" to name,
                    "quantity" to qty,
                    "category" to cat,
                    "expiryDate" to expiryDate
                )

                db.collection("foodItems")
                    .add(food)
                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Food added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                    .addOnFailureListener { error ->

                        Toast.makeText(
                            this,
                            "Error: ${error.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }
}