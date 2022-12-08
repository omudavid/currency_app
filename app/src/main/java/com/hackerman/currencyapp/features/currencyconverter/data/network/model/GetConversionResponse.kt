package com.hackerman.currencyapp.features.currencyconverter.data.network.model


import com.google.gson.annotations.SerializedName

data class GetConversionResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("info")
    val info: Info,
    @SerializedName("query")
    val query: Query,
    @SerializedName("result")
    val result: Double,
    @SerializedName("success")
    val success: Boolean
)