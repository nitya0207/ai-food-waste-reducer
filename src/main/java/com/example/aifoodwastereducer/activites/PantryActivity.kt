package com.example.aifoodwastereducer.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aifoodwastereducer.R
import com.google.firebase.firestore.FirebaseFirestore

class PantryActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var foodContainer: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantry)

        db = FirebaseFirestore.getInstance()

        val addFoodButton = findViewById<Button>(R.id.btnAddFood)
        foodContainer = findViewById(R.id.foodContainer)
        progressBar = findViewById(R.id.progressBar)
        emptyText = findViewById(R.id.tvEmpty)

        addFoodButton.setOnClickListener {
            startActivity(
                Intent(this, AddFoodActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        // Reload pantry whenever user comes back from Add Food
        loadFoodItems()
    }

    private fun loadFoodItems() {

        progressBar.visibility = View.VISIBLE
        emptyText.visibility = View.GONE

        // Clear old items before loading again
        foodContainer.removeAllViews()

        db.collection("foodItems")
            .get()
            .addOnSuccessListener { result ->

                progressBar.visibility = View.GONE

                if (result.isEmpty) {

                    emptyText.visibility = View.VISIBLE

                } else {

                    for (document in result) {

                        val name =
                            document.getString("name") ?: "Unknown"

                        val quantity =
                            document.getString("quantity") ?: "-"

                        val category =
                            document.getString("category") ?: "-"

                        val expiry =
                            document.getString("expiryDate") ?: "-"

                        val foodView = TextView(this)

                        foodView.text =
                            "$name\n" +
                                    "Quantity: $quantity\n" +
                                    "Category: $category\n" +
                                    "Expiry: $expiry"

                        foodView.textSize = 16f

                        foodView.setPadding(
                            30,
                            25,
                            30,
                            25
                        )

                        val params =
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                        params.setMargins(
                            0,
                            0,
                            0,
                            20
                        )

                        foodView.layoutParams = params

                        foodView.setBackgroundColor(
                            getColor(R.color.white)
                        )

                        foodContainer.addView(foodView)
                    }
                }
            }

            .addOnFailureListener { error ->

                progressBar.visibility = View.GONE

                Toast.makeText(
                    this,
                    "Failed to load food: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}