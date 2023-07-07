package com.plz.no.anr.lol.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

internal class RetrofitGenerator {
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

    @OptIn(ExperimentalSerializationApi::class)
    internal val client: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
}


