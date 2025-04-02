package com.example.expensetrackerapp1.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetrackerapp1.R
import com.google.gson.Gson
import java.io.IOException

data class Expense(val name: String, val amount: Double, val currency: String, val convertedCurrency: Double)

class RvAdapter(private val expenses: MutableList<Expense>, private val context: Context, private val fragment: Fragment):
    RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    class RvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.ExpenseName)
        val amountView: TextView = itemView.findViewById(R.id.ExpenseAmount)
        val deleteButton: Button = itemView.findViewById(R.id.Delete)
        val detailsButton: Button = itemView.findViewById(R.id.Details)
        val currencyView: TextView =itemView.findViewById(R.id.Currency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        RvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout,parent,false))

    override fun getItemCount() = expenses.size

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val expense = expenses[position]
        holder.expenseName.text = expense.name
        holder.amountView.text = String.format("%.2f", expense.convertedCurrency)
        holder.currencyView.text = expense.currency

        holder.deleteButton.setOnClickListener {
            expenses.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
            saveExpensesToFile(context, expenses)
        }
        holder.detailsButton.setOnClickListener{
            val bundle = Bundle().apply {
                putString("expense", expense.name)
                putDouble("amount", expense.amount)
                putString("currency", expense.currency)
                putDouble("convertedCurrency", expense.convertedCurrency)
            }
            fragment.findNavController().navigate(R.id.action_expenseListFragment_to_expenseDetailsFragment, bundle)
            }
    }
    private fun saveExpensesToFile(context: Context, expenses: List<Expense>) {
        try {
            val json = Gson().toJson(expenses)
            context.openFileOutput("expenses.json", Context.MODE_PRIVATE).use { output ->
                output.write(json.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
