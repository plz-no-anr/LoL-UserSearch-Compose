package com.plznoanr.lol.core.network.config

import com.plznoanr.lol.core.network.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class ApiConfiguration {
    init {
        Timber.d("$this created.")
    }

    val endPoint: String
        get() = BuildConfig.RIOT_BASE_URL

    val headerInterceptor: Interceptor
        get() = Interceptor { chain ->
            val request: Request = chain.request()
            val requestBuilder = request.newBuilder()

            runBlocking {
                val headers = defaultHeader()
                headers.keys
                    .filterNot { request.headers.names().contains(it) }
                    .forEach { key ->
                        headers[key]?.let { value ->
                            requestBuilder.addHeader(key, value)
                        }
                    }
            }

            chain.proceed(requestBuilder.build())
        }

    val logInterceptor: Interceptor
        get() = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

    val connectTimeout: Long
        get() = 60L
    val writeTimeout: Long
        get() = 60L
    val readTimeout: Long
        get() = 60L

    val defaultHeader: suspend () -> Map<String, String> = {
        HashMap<String, String>().apply {
            put("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
            put("Origin", "https://developer.riotgames.com")
        }
    }

}