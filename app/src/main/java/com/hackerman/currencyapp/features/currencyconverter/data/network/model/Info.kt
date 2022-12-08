package com.hackerman.currencyapp.features.currencyconverter.data.network.model


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("timestamp")
    val timestamp: Int
)