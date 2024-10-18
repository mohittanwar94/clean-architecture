package com.example.data.api

import com.example.data.model.ProductDto
import retrofit2.http.GET

interface ApiService {

    @GET("/products/")
    suspend fun getAllProducts(): ProductDto
}