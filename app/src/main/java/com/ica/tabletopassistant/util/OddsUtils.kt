package com.ica.tabletopassistant.util

import kotlin.math.ceil
import kotlin.math.floor

// roundingMode:
//  0 - Up
//  1 - Nearest Half
//  2 - Down
//  3 - Standard
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
            {
                ratio = roundFloatUp(ratio, defend > attack)
            }
            else if (roundingMode == 1) // to nearest
            {
                ratio = roundFloatToNearestHalf(ratio, defend > attack)
            }
            else if (roundingMode == 2) // down
            {
                ratio = roundFloatDown(ratio, defend > attack)
            }
            else if (roundingMode == 3) // standard
            {
                ratio = roundFloatToNearestWhole(ratio, defend > attack)
            }

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
