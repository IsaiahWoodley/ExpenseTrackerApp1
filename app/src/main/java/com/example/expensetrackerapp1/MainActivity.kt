package com.example.expensetrackerapp1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val expenses = ArrayList<Expense>()
    private lateinit var rvAdapter: RvAdapter
    private lateinit var footerFragment: FooterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Activity Lifecycle", "onCreate was called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        replaceFragment(HeaderFragment(), R.id.fragment_container_header)

        // Add FooterFragment dynamically and store reference
        footerFragment = FooterFragment()
        replaceFragment(footerFragment, R.id.fragment_container_footer)


        val editTextText = findViewById<EditText>(R.id.editTextText)
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        var button = findViewById<Button>(R.id.button)
        var financialButton = findViewById<Button>(R.id.financial)

        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rvAdapter = RvAdapter(expenses)
        recyclerView.adapter = rvAdapter

        button.setOnClickListener {
            val name = editTextText.text.toString().trim()
            val amount = editTextNumber.text.toString().trim()

            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val amount = amount.toDoubleOrNull() ?: 0.0
                val newExpense = Expense(name, amount)
                expenses.add(newExpense)
                rvAdapter.notifyItemInserted(expenses.size - 1)

                updateFooter(amount)
                editTextText.text.clear()
                editTextNumber.text.clear()
            }
        }
        financialButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.canada.ca/en/financial-consumer-agency/services/savings-investments/choose-financial-advisor.html"))
            startActivity(intent)
        }

    }

    private fun updateFooter(amount: Double) {
        footerFragment.updateTotal(amount)
    }
    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
    override fun onResume() {
        super.onResume()
        Log.d("Activity Lifecycle", "onResume was called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity Lifecycle", "onPause was called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity Lifecycle", "onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity Lifecycle", "onDestroy was called")
    }
}

