package com.mvalu.bettr_api

import com.mvalu.bettr_api.network.ResponseCallback
import com.mvalu.bettr_api.utils.BettrApiSdkLogger

object BettrApiSdk {
    private const val TAG = "BettrApiSdk"
    private var ACCESS_KEY: String = ""
    private var SECRET_KEY: String = ""
    private var ORGANIZATION_ID: String = ""
    private var USER_ID: String = ""
    private var isSdkInitialized = false
    private var isLoggingBehaviourEnabled: Boolean = false

    fun isSdkInitialized(): Boolean {
        return isSdkInitialized
    }

    fun isLoggingBehaviourEnabled(): Boolean {
        return isLoggingBehaviourEnabled
    }

    fun getAccessKey(): String {
        return ACCESS_KEY
    }

    fun getSecretKey(): String {
        return SECRET_KEY
    }

    fun getOrganizationId(): String {
        return ORGANIZATION_ID
    }

    fun getUserId(): String {
        return USER_ID
    }

    /**
     * Initialize sdk here with required parameters
     * if required parameters are null or empty throw error
     */
    fun initializeSDK(
        ACCESS_KEY: String,
        SECRET_KEY: String,
        ORGANIZATION_ID: String,
        USER_ID: String
    ) {
        if (isSdkInitialized) {
            BettrApiSdkLogger.printError(TAG, "Sdk already initialized")
        } else {
            if (ACCESS_KEY.isNullOrEmpty()) {
                throw IllegalArgumentException("ACCESS_KEY is null or empty")
            }
            if (SECRET_KEY.isNullOrEmpty()) {
                throw IllegalArgumentException("SECRET_KEY is null or empty")
            }
            if (ORGANIZATION_ID.isNullOrEmpty()) {
                throw IllegalArgumentException("ORGANIZATION_ID is null or empty")
            }
            if (USER_ID.isNullOrEmpty()) {
                throw IllegalArgumentException("USER_ID is null or empty")
            }

            this.ACCESS_KEY = ACCESS_KEY
            this.SECRET_KEY = SECRET_KEY
            this.ORGANIZATION_ID = ORGANIZATION_ID
            this.USER_ID = USER_ID
            BettrApiSdkLogger.printInfo(
                TAG,
                "Sdk initialization initiated with " +
                        "ACCESS_KEY: $ACCESS_KEY SECRET_KEY: $SECRET_KEY " +
                        "ORGANIZATION_ID: $ORGANIZATION_ID USER_ID: $USER_ID"
            )
        }
    }

    /**
     * enable logging behaviour to see logs in logcat
     */
    fun enableLoggingBehaviour(enableLoggingBehaviour: Boolean) {
        this.isLoggingBehaviourEnabled = enableLoggingBehaviour
    }

    fun foo() {

    }

    fun getDetails(responseCallback: ResponseCallback<TestModel>) {
        responseCallback.onSuccess(TestModel())
    }
}