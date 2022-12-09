package com.hackerman.currencyapp.common.extension

import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.hackerman.currencyapp.R

fun Fragment.showErrorSnackBar(view: View, message: String) {
    Snackbar.make(
        view,
        message,
        5000
    ).setActionTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error_color))
        .setAction("ok") {
        }.show()
}


fun Fragment.showProgressBar(
    progressBar: ProgressBar
) {
    progressBar.visibility = View.VISIBLE
}

fun Fragment.hideProgressBar(
    progressBar: ProgressBar
) {
    progressBar.visibility = View.INVISIBLE
}