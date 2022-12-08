package com.hackerman.currencyapp.common.errorhelper

import android.content.Context

import com.hackerman.currencyapp.R
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandler @Inject constructor(var context: Context) {
    fun parse(exception: Exception): String {
        return when (exception) {
            is HttpException -> {
                return try {
                    var errorMessage = ""
                    val obj = exception.response()?.errorBody()?.string()?.let {
                        JSONObject(
                            it,

                            )
                    }

                    errorMessage = obj!!.getString("message")
                    return errorMessage.trim()
                } catch (exp: Exception) {
                    context.getString(R.string.error_exception_unknown_error)
                }
            }
            is UnknownHostException -> context.getString(R.string.error_exception_network_error)
            is SocketTimeoutException -> context.getString(R.string.error_exception_time_out_error)
            else -> exception.message ?: context.getString(R.string.error_exception_unknown_error)
        }
    }
}