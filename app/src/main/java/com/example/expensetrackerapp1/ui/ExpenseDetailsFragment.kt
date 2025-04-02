package com.example.expensetrackerapp1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.expensetrackerapp1.R

class ExpenseDetailsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expense_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val expenseName = arguments?.getString("expense")
        val expenseAmount = arguments?.getDouble("amount")
        val convertedAmount =arguments?.getDouble("convertedCurrency")
        val convertedCurrency = arguments?.getString("currency")

        view.findViewById<TextView>(R.id.expenseName).text = "Name: $expenseName Amount: $$expenseAmount CAD"
        view.findViewById<TextView>(R.id.expenseConverted).text = "Converted Amount: ${"%,.2f".format(convertedAmount)} $convertedCurrency"
    }
}