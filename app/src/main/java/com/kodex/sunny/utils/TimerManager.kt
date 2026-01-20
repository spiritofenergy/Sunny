package com.kodex.sunny.utils

import java.util.Timer
import java.util.TimerTask
import javax.inject.Singleton

@Singleton
class TimerManager() {

    private var timer: Timer? = null

    fun startTimer(task: TimerTask){
        timer?.cancel()
        timer = Timer()
        timer?.schedule(task, 10, 10)
    }
    fun stopTimer(){
        timer?.cancel()
        timer= null
    }
}