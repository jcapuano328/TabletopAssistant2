package com.ica.tabletopassistant.util

import kotlin.math.floor
import kotlin.math.ceil
import kotlin.random.Random

class MathUtils {
    fun randomDie6(): Int {
        return generateRandomNumber(1, 6)
    }

    private fun generateRandomNumber(min: Int, max: Int): Int {
        require(min <= max) { "Min value must be less than or equal to max value." }

        if (min == max) {
            return min
        }
        val range = max - min + 1
        return Random.nextInt(range) + min
    }

    fun roundFloatToNearestHalf(value: Float, roundUp: Boolean): Float {
        val roundedValue = if (roundUp) ceil(value * 2) / 2 else floor(value * 2) / 2
        return if (roundedValue < 0.5f) 0.5f else roundedValue
    }
}