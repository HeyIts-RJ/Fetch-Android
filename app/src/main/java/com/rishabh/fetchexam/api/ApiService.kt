package com.rishabh.fetchexam.api

import com.rishabh.fetchexam.models.Items
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/hiring.json")
    suspend fun getHiringJson(): Response<Items>
}