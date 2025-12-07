package com.ica.tabletopassistant.util

import kotlin.random.Random

fun randomDie6(): Int {
    return generateRandomNumber(1, 6)
}
fun randomDie8(): Int {
    return generateRandomNumber(1, 8)
}
fun randomDie10(): Int {
    return generateRandomNumber(1, 10)
}

private fun generateRandomNumber(min: Int, max: Int): Int {
    require(min <= max) { "Min value must be less than or equal to max value." }

    if (min == max) {
        return min
    }
    val range = max - min + 1
    return Random.nextInt(range) + min
}
