package ru.netology.nmedia.data.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideKeyboard() {
    val inn = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inn.hideSoftInputFromWindow(windowToken, 0)
}

internal fun View.showKeyboard() {
    val inn = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inn.showSoftInput(this, 0)
}