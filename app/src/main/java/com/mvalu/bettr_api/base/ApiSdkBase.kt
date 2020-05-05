package com.mvalu.bettr_api.base

import com.mvalu.bettr_api.network.ApiTag
import com.mvalu.bettr_api.network.ServiceApi
import com.mvalu.bettr_api.utils.BettrApiSdkLogger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class ApiSdkBase {
    @Inject
    lateinit var serviceApi: ServiceApi

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun callApi(observable: Observable<out Any>, apiTag: ApiTag): Disposable {
        var disposable = observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response: Any? ->
                    @Suppress("UNCHECKED_CAST")
                    var httpResponse = response as Response<Any>
                    BettrApiSdkLogger.printInfo(httpResponse.code().toString(), apiTag.name)
                    if (httpResponse.code() == 200) {
                        onApiSuccess(apiTag, httpResponse.body()!!)
                    } else {
                        var errorResponse = httpResponse.errorBody()
                        when (httpResponse.code()) {
                            401 -> {
                                onAuthError(apiTag)
                            }
                            500 -> {
                                onApiError(apiTag, getErrorMessage(errorResponse!!))
                            }
                            else -> {
                                onApiError(apiTag, getErrorMessage(errorResponse!!))
                            }
                        }
                    }

                },
                { error: Throwable? ->
                    when (error) {
                        is HttpException -> {
                            onApiError(apiTag, getErrorMessage(error.response()?.errorBody()!!))
                        }
                        is SocketTimeoutException -> {
                            onTimeout(apiTag)
                        }
                        is IOException -> {
                            onNetworkError(apiTag)
                        }
                        else -> {
                            onApiError(apiTag, error?.message!!)
                        }
                    }
                }
            )
        compositeDisposable.add(disposable)
        return disposable
    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            val errorObject = jsonObject.getJSONObject("error")
            errorObject.getString("message")
        } catch (e: Exception) {
            e.message!!
        }
    }

    //Api callback methods
    abstract fun onApiSuccess(apiTag: ApiTag, response: Any)

    abstract fun onApiError(apiTag: ApiTag, errorMessage: String)
    abstract fun onTimeout(apiTag: ApiTag)
    abstract fun onNetworkError(apiTag: ApiTag)
    abstract fun onAuthError(apiTag: ApiTag)
}