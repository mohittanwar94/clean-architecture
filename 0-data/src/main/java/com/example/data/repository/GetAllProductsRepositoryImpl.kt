package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.model.toDomainProduct
import com.example.domain.model.Product
import com.example.domain.repository.GetAllProductsRepository
import javax.inject.Inject

class GetAllProductsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GetAllProductsRepository {

    override suspend fun getAllProducts(): List<Product> {
        val productDto = apiService.getAllProducts()
        return if (productDto.products.isEmpty()) {
            emptyList()
        } else {
            productDto.products.map {
                it.toDomainProduct()
            }
        }
    }
}