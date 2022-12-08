package com.hackerman.currencyapp.features.currencyconverter.data.network.model


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String
)