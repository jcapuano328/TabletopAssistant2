package com.ica.tabletopassistant.util

import kotlin.math.floor
import kotlin.math.ceil
import kotlin.math.round
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

    fun calcOdds(attack: Float, defend: Float, isRounded: Boolean, roundingMode: Int): String {
        var oddsString = "NaN" // Renamed to avoid confusion with the numeric 'odds'
        //println("attack: $attack, defend: $defend, isRounded: $isRounded, roundingMode: $roundingMode")
        if (attack > 0 && defend > 0) {
            var ratio = if (attack >= defend) (attack / defend) else (defend / attack)
            //println("ratio: $ratio")
            if (isRounded) {
                // Your rounding logic based on roundingMode
                //println("roundingMode: $roundingMode")
                if (roundingMode == 0) // up
                    ratio = (ceil(ratio * 1000f) / 1000f) // Consider if you want this precision before formatting
                else if (roundingMode == 1) // to nearest
                    //ratio = (round(ratio * 1000f) / 1000f) // Consider if you want this precision before formatting
                    if (attack > defend)
                        ratio = roundFloatToNearestHalf(ratio, false)
                    else
                        ratio = roundFloatToNearestHalf(ratio, true)
                else if (roundingMode == 2) // down
                    ratio = (floor(ratio * 1000f) / 1000f) // Consider if you want this precision before formatting

                //println("Rounded ratio: $ratio")
            }

            // Determine the number of decimal places for formatting
            val decimalPlaces = if (ratio.isWholeNumber()) 0 else 1 // Or any other number of places you need

            oddsString = if (attack >= defend) {
                String.format("%.${decimalPlaces}f : 1", ratio)
            } else {
                String.format("1 : %.${decimalPlaces}f", ratio)
            }
        }

        //println("oddsString: $oddsString")
        return oddsString
    }

    fun Float.isWholeNumber(): Boolean {
        return this % 1 == 0f
    }
}