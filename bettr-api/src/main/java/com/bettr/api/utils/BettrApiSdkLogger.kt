package com.bettr.api.utils

import android.util.Log
import com.bettr.api.BettrApiSdk

object BettrApiSdkLogger {

    fun printInfo(tag: String, message: String) {
        if (BettrApiSdk.isLoggingBehaviourEnabled()) {
            Log.i(tag, message)
        }
    }

    fun printError(tag: String, message: String) {
        if (BettrApiSdk.isLoggingBehaviourEnabled()) {
            Log.e(tag, message)
        }
    }

}