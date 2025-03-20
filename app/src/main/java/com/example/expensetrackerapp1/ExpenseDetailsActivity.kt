package com.example.expensetrackerapp1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExpenseDetailsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        val expenseName = intent.getStringExtra("expense")
        val expenseAmount = intent.getStringExtra("amount")

        findViewById<TextView>(R.id.expenseName).text = "Name: $expenseName"
        findViewById<TextView>(R.id.expenseAmount).text = "Amount: $$expenseAmount"

        }
    }
