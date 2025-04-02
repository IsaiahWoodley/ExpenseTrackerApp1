package com.example.expensetrackerapp1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.expensetrackerapp1.R


class FooterFragment : Fragment() {
    private lateinit var footerText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_footer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        footerText = view.findViewById(R.id.footerText)
    }

    fun updateTotal(amount: Double) {
        if (::footerText.isInitialized) {
            footerText.text = "Total: $$amount"
        }
    }
}