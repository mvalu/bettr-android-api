package com.mvalu.bettr_api.network

interface ErrorCallback {
    fun onApiSuccess(apiTag: ApiTag, response: Any)
    fun onApiError(apiTag: ApiTag, errorMessage: String)
    fun onTimeout(apiTag: ApiTag)
    fun onNetworkError(apiTag: ApiTag)
    fun onAuthError(apiTag: ApiTag)
}