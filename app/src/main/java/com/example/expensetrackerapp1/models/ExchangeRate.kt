package com.example.expensetrackerapp1.models

//Data class to receive a quote from the API

data class ExchangeRate(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

