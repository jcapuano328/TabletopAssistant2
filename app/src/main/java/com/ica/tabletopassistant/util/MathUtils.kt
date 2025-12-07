package com.ica.tabletopassistant.util

fun Float.isWholeNumber(): Boolean {
    return this % 1 == 0f
}
