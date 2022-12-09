package com.hackerman.currencyapp.features.details.data.network.model

import com.google.gson.annotations.SerializedName

data class GetPopularCurrenciesResponse (
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: HashMap<String, Double>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long,
)