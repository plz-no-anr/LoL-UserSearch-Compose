package com.plz.no.anr.lol.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.plz.no.anr.lol.data.api.config.ApiConfiguration
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSerializationApi::class)
internal class RetrofitGenerator(
    private val apiConfiguration: ApiConfiguration
) {

    val client: Retrofit
        get() = retrofit {
            val okHttpClient = okHttpClientInit()
            baseUrl(apiConfiguration.endPoint)
            client(okHttpClient)
            addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
        }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowStructuredMapKeys = true
        prettyPrint = true
        coerceInputValues = true
        useArrayPolymorphism = true
        allowSpecialFloatingPointValues = true
        useAlternativeNames = true
    }

    private fun okHttpClientInit(): OkHttpClient = okhttp {
        connectTimeout(apiConfiguration.connectTimeout, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
        writeTimeout(apiConfiguration.writeTimeout, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
        readTimeout(apiConfiguration.readTimeout, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
        addInterceptor(apiConfiguration.headerInterceptor) // header 삽입
        apiConfiguration.logInterceptor?.also {
            addInterceptor(it) // log interceptor 추가
        }
    }
}

private fun retrofit(init: Retrofit.Builder.() -> Unit): Retrofit =
    Retrofit.Builder()
        .apply(init)
        .build()

private fun okhttp(init: OkHttpClient.Builder.() -> Unit): OkHttpClient =
    OkHttpClient.Builder()
        .apply(init)
        .build()