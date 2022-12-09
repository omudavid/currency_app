package com.hackerman.currencyapp.features.details.data.network.model


import com.google.gson.annotations.SerializedName

data class GetHistoricDataResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("rates")
    val rates: HashMap<String,HashMap<String,Double>?>?,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timeseries")
    val timeseries: Boolean
)