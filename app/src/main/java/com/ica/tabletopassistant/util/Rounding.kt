package com.ica.tabletopassistant.util

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

fun roundFloatUp(value: Float, flag: Boolean) : Float {
    if (flag) {
        return floor(value)
    }
    return ceil(value)
}

fun roundFloatToNearestHalf(value: Float, flag: Boolean): Float {
    //val roundedValue = if (roundUp) ceil(value * 2) / 2 else floor(value * 2) / 2
    //return if (roundedValue < 0.5f) 0.5f else roundedValue
    if (flag) {
        (round(-1f * value * 2) / 2f) * -1f
    }
    return (round(value * 2) / 2f)
}

fun roundFloatDown(value: Float, flag: Boolean) : Float {
    if (flag) {
        return ceil(value)
    }
    return floor(value)
}

fun roundFloatToNearestWhole(value: Float, flag: Boolean): Float {
    if (flag) {
        return round(value*-1f).toInt().toFloat() * -1f
    }
    return round(value).toInt().toFloat()
}

