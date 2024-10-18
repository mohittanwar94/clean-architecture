package com.example.domain.repository

import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface GetAllProductsRepository {

    suspend fun getAllProducts(): List<Product>
}