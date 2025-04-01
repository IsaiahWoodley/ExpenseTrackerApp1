package com.example.expensetrackerapp1.network

import com.example.expensetrackerapp1.models.ExchangeRate
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApiService {
    @GET("currencies")
    suspend fun getCurrencyList(): Map<String, String>

    @GET("latest")
    suspend fun getExchangeRates(@Path("base") base: String): ExchangeRate
}