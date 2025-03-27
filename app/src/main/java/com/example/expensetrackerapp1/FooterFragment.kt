package com.example.expensetrackerapp1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FooterFragment : Fragment() {
    private lateinit var footerText: TextView
    private var totalAmount: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val footer = inflater.inflate(R.layout.fragment_footer, container, false)
        footerText = footer.findViewById(R.id.footerText)
        updateTotal(0.0)
        return footer
    }
    @SuppressLint("SetTextI18n")
    fun updateTotal(amount: Double) {
        totalAmount += amount
        footerText.text = "Total Expenses: $$totalAmount"
    }
}