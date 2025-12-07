package com.ica.tabletopassistant

import org.junit.Test

import org.junit.Assert.*


import com.ica.tabletopassistant.util.roundFloatUp
import com.ica.tabletopassistant.util.roundFloatToNearestHalf
import com.ica.tabletopassistant.util.roundFloatDown
import com.ica.tabletopassistant.util.roundFloatToNearestWhole

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RoundingTest {


    // roundFloatUp
    @Test
    fun roundFloatUp_1point2_2point0() {
        assertEquals(2.0f, roundFloatUp(1.2f, false))
    }
    @Test
    fun roundFloatUp_1point5_2point0() {
        assertEquals(2.0f, roundFloatUp(1.5f, false))
    }
    @Test
    fun roundFloatUp_1point76_2point0() {
        assertEquals(2.0f, roundFloatUp(1.76f, false))
    }

    @Test
    fun roundFloatUp_1point2_1point0() {
        assertEquals(1.0f, roundFloatUp(1.2f, true))
    }
    @Test
    fun roundFloatUp_1point5_1point0() {
        assertEquals(1.0f, roundFloatUp(1.5f, true))
    }
    @Test
    fun roundFloatUp_1point76_1point0() {
        assertEquals(1.0f, roundFloatUp(1.76f, true))
    }

    //roundFloatToNearestHalf
    @Test
    fun roundFloatToNearestHalf_1point2_1point0() {
        assertEquals(1.0f, roundFloatToNearestHalf(1.2f, false))
    }
    @Test
    fun roundFloatToNearestHalf_1point3_1point5() {
        assertEquals(1.5f, roundFloatToNearestHalf(1.3f, false))
    }
    @Test
    fun roundFloatToNearestHalf_1point5_1point5() {
        assertEquals(1.5f, roundFloatToNearestHalf(1.5f, false))
    }
    @Test
    fun roundFloatToNearestHalf_1point71_1point5() {
        assertEquals(1.5f, roundFloatToNearestHalf(1.71f, false))
    }
    @Test
    fun roundFloatToNearestHalf_1point76_2point0() {
        assertEquals(2.0f, roundFloatToNearestHalf(1.76f, false))
    }


    // roundFloatDown
    @Test
    fun roundFloatDown_1point2_1point0() {
        assertEquals(1.0f, roundFloatDown(1.2f, false))
    }
    @Test
    fun roundFloatDown_1point5_1point0() {
        assertEquals(1.0f, roundFloatDown(1.5f, false))
    }
    @Test
    fun roundFloatDown_1point76_1point0() {
        assertEquals(1.0f, roundFloatDown(1.76f, false))
    }

    @Test
    fun roundFloatDown_1point2_2point0() {
        assertEquals(2.0f, roundFloatDown(1.2f, true))
    }
    @Test
    fun roundFloatDown_1point5_2point0() {
        assertEquals(2.0f, roundFloatDown(1.5f, true))
    }
    @Test
    fun roundFloatDown_1point76_2point0() {
        assertEquals(2.0f, roundFloatDown(1.76f, true))
    }

}