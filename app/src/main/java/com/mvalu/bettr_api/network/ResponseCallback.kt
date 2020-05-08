package com.mvalu.bettr_api.network

interface ResponseCallback<T> {
    fun onSuccess(response: T)
}