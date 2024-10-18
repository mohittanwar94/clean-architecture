package com.example.domain.usecase

import com.example.common.network.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repository.GetAllProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllProductUseCase @Inject constructor(private val getAllProductsRepository: GetAllProductsRepository) {

    operator  fun invoke(): Flow<ResultWrapper<List<Product>>> = flow {
        try {
            emit(ResultWrapper.Loading())
            val response = getAllProductsRepository.getAllProducts()
            emit(ResultWrapper.Success(response))
        } catch (e: HttpException) {
            emit(ResultWrapper.Error(e.message ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(ResultWrapper.Error(e.message ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Unknown Error"))
        }
    }
}