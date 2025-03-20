package com.example.expensetrackerapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Expense(val name: String, val amount: String)

class RvAdapter(private val expenses: ArrayList<Expense>): RecyclerView.Adapter<RvAdapter.RvViewHolder>() {
    class RvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.ExpenseName)
        val amountView: TextView = itemView.findViewById(R.id.ExpenseAmount)
        val deleteButton: Button = itemView.findViewById(R.id.Delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        RvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout,parent,false))

    override fun getItemCount() = expenses.size

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val expense = expenses[position]
        holder.expenseName.text = expense.name
        holder.amountView.text = "$${expense.amount}"

        holder.deleteButton.setOnClickListener {
            expenses.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
        }
    }


}
