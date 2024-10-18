package com.example.data.hilt

import com.example.common.Constants
import com.example.data.api.ApiService
import com.example.data.network.retrofit.factory.ResponseAdapterFactory
import com.example.data.repository.GetAllProductsRepositoryImpl
import com.example.domain.repository.GetAllProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): ApiService = Retrofit.Builder().baseUrl(Constants.BASEURL).addCallAdapterFactory(ResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideGetAllProductRepository(apiService: ApiService): GetAllProductsRepository =
        GetAllProductsRepositoryImpl(apiService)
}