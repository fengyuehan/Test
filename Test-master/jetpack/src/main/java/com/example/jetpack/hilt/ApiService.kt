package com.example.jetpack.hilt

import retrofit2.http.GET

interface ApiService {
    @GET("api/china")
    suspend fun getProvinces(): List<Province>
}