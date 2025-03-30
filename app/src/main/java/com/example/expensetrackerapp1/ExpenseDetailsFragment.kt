package com.example.expensetrackerapp1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ExpenseDetailsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expense_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val expenseName = arguments?.getString("expense")
        val expenseAmount = arguments?.getDouble("amount")

        view.findViewById<TextView>(R.id.expenseName).text = "Name: $expenseName Amount: $$expenseAmount"

    }
}