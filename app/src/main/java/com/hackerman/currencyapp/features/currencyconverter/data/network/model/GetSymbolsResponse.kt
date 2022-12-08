package com.hackerman.currencyapp.features.currencyconverter.data.network.model


import com.google.gson.annotations.SerializedName

data class GetSymbolsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("symbols")
    val symbols: HashMap<String,String>?
)