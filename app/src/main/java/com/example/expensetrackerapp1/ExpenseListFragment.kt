package com.example.expensetrackerapp1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

private val FILE_NAME = "expenses.json"

class ExpenseListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextText: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var button: Button
    private lateinit var financialButton: Button
    private val expenses = mutableListOf<Expense>()
    private lateinit var rvAdapter: RvAdapter
    private var footerFragment: FooterFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editTextText = view.findViewById(R.id.editTextText)
        editTextNumber = view.findViewById(R.id.editTextNumber)
        button = view.findViewById(R.id.button)
        financialButton = view.findViewById(R.id.financial)

        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        expenses.addAll(loadExpensesFromFile(requireContext()))
        rvAdapter = RvAdapter(expenses,requireContext(), this)
        recyclerView.adapter = rvAdapter

        footerFragment = parentFragmentManager.findFragmentById(R.id.fragment_container_footer) as? FooterFragment
        updateFooter()

        button.setOnClickListener {
            val name = editTextText.text.toString().trim()
            val amount = editTextNumber.text.toString().trim()

            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val amount = amount.toDoubleOrNull() ?: 0.0
                val newExpense = Expense(name, amount)
                expenses.add(newExpense)
                rvAdapter.notifyItemInserted(expenses.size - 1)

                saveExpenseToFile(requireContext(), expenses)
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
        footerFragment?.updateTotal(total)
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
}