package com.kodex.sunny.utils

import android.annotation.SuppressLint
import android.icu.util.Calendar
import java.text.SimpleDateFormat

@SuppressLint("ConstantLocale","SimpleDateFormat")
object TimeUtils {
  private  val timerFormatter = SimpleDateFormat("HH:mm:ss:SS")
  private  val trackNameFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS")

    fun getTimerTime(startTimerInMillis: Long): String {
        val elapsedTimeInMillis = System.currentTimeMillis() - startTimerInMillis
        val cv = Calendar.getInstance()
        cv.timeInMillis = elapsedTimeInMillis
        return timerFormatter.format(cv.time)
    }
    fun getDateAndTime(): String {
        val cv = Calendar.getInstance()
        return trackNameFormatter.format(cv.time)
    }
}

