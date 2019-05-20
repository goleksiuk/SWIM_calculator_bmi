package com.example.myapplication

import logic.BMIForKgCm
import logic.BMIForLbIn
import org.junit.Assert
import org.junit.Test

class TestLogic {

    @Test
    fun testBMIForKgCm(){
        val result = BMIForKgCm(62, 180).calculateBmi()
        Assert.assertEquals(19.135, result, 0.001)
    }

    @Test
    fun testBMIForLbIn(){
        val result = BMIForLbIn(137, 69).calculateBmi()
        Assert.assertEquals(20.229, result, 0.001)
    }
}