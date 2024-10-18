package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.network.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.usecase.GetAllProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val getAllProductUseCase: GetAllProductUseCase) :
    ViewModel() {
    private val _stateUi = MutableStateFlow(UiState(isLoading = true))
    val stateUi = _stateUi.asStateFlow()

    private val _liveData = MutableLiveData("")
    val liveData get()  = _liveData

    fun setValueFlow() {
        _liveData.postValue("kutta")
        _liveData.postValue("kutta")
        viewModelScope.launch {
            getAllProductUseCase.invoke().collect {
                println("data=======${it.data}")
                when (it) {
                    is ResultWrapper.Success -> {
                        _stateUi.value=UiState(data = it.data)
                    }

                    else -> {
                       _stateUi.value= UiState(message = it.error)
                    }
                }
            }
        }
    }

    class UiState(
        var data: List<Product>? = emptyList(),
        var message: String? = null,
        var isLoading: Boolean = false
    )
}