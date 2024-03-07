package com.plznoanr.lol.core.network.retrofit

import com.plznoanr.lol.core.network.model.AccountResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface RiotAccountApi {

    @GET("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    suspend fun getAccount(
        @HeaderMap headerMap: Map<String, String>,
        @Path("gameName") name: String,
        @Path("tagLine") tag: String
    ): Response<AccountResponse?>

}