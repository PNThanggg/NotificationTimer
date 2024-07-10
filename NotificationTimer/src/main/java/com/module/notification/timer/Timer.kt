package com.module.notification.timer

import android.content.Context

interface Timer {
    fun play(context: Context, timeMillis: Long)

    fun pause(context: Context)

    fun stop(context: Context)

    fun terminate(context: Context)
}