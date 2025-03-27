package com.example.expensetrackerapp1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileNotFoundException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

private val FILE_NAME = "expenses.json"

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val expenses = mutableListOf<Expense>()
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

        expenses.addAll(loadExpensesFromFile(this))
        rvAdapter = RvAdapter(expenses, this)
        recyclerView.adapter = rvAdapter
        updateFooter()

        button.setOnClickListener {
            val name = editTextText.text.toString().trim()
            val amount = editTextNumber.text.toString().trim()

            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val amount = amount.toDoubleOrNull() ?: 0.0
                val newExpense = Expense(name, amount)
                expenses.add(newExpense)
                rvAdapter.notifyItemInserted(expenses.size - 1)

                saveExpenseToFile(this, expenses)
                updateFooter()
                editTextText.text.clear()
                editTextNumber.text.clear()
            }
        }
        financialButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.canada.ca/en/financial-consumer-agency/services/savings-investments/choose-financial-advisor.html"))
            startActivity(intent)
        }

    }

    private fun updateFooter() {
        val total = expenses.sumOf { it.amount }
        footerFragment.updateTotal(total)
    }
    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
    private fun saveExpenseToFile(context: Context, expenses : List<Expense>) {
        try {
            val json = Gson().toJson(expenses)
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { output ->
                output.write(json.toByteArray())
            }
            Log.d("FileStorage", "Expenses saved successfully")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error saving expense: ${e.message}")
        }
    }
    private fun loadExpensesFromFile(context: Context): MutableList<Expense> {
        val expenseList: MutableList<Expense> = mutableListOf()
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return expenseList

            val json = file.readText()
            val type = object : TypeToken<List<Expense>>() {}.type
            val loadedExpense: List<Expense> = Gson().fromJson(json, type)
            expenseList.addAll(loadedExpense)

            Log.d("FileStorage", "Expenses loaded successfully")
        } catch (e: FileNotFoundException) {
            Log.e("FileStorage", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error reading file: ${e.message}")
        }
        return expenseList
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

