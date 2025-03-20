package com.example.expensetrackerapp1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val expenses = ArrayList<Expense>()
    private lateinit var rvAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editTextText = findViewById<EditText>(R.id.editTextText)
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        var button = findViewById<Button>(R.id.button)

        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rvAdapter = RvAdapter(expenses)
        recyclerView.adapter = rvAdapter

        button.setOnClickListener {
            val name = editTextText.text.toString().trim()
            val amount = editTextNumber.text.toString().trim()

            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val newExpense = Expense(name, amount)
                expenses.add(newExpense)
                rvAdapter.notifyItemInserted(expenses.size - 1)

                editTextText.text.clear()
                editTextNumber.text.clear()
            }
        }
    }
}

