package com.example.apisampleapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apisampleapp.data.model.response.Quotation
import com.example.apisampleapp.domain.InvestRepository
import com.example.apisampleapp.ui.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class InvestViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InvestUiState())
    val uiState: StateFlow<InvestUiState> = _uiState.asStateFlow()


    private val repository = InvestRepository()

    fun onQueryChanged(query: String) {
        _uiState.update { currentState ->
            currentState.copy(query = query)
        }
    }

    fun findInstrument() {
        loading()
        viewModelScope.launch {
            try {
                val result = repository.findInstrument(_uiState.value.query)
                val errorBody = result.errorBody()
                val body = result.body()
                when {
                    // Error
                    errorBody != null -> {
                        error(errorBody)
                    }
                    // Success
                    body != null -> {
                        content(body.instruments.map { Item(it.uid, it.name, it.ticker) })
                    }
                }
            } catch (e: Exception) {
                error(e)
            }
        }
    }

    fun getLastPrices() {
        viewModelScope.launch {
            try {
                val result = repository.getLastPrices(_uiState.value.items.map { it.uid })

                val errorBody = result.errorBody()
                val body = result.body()
                when {
                    // Error
                    errorBody != null -> {
                        error(errorBody)
                    }
                    // Success
                    body != null -> {
                        content(_uiState.value.items.map { item ->
                            item.copy(price = body.lastPrices.find { it.instrumentUid == item.uid }?.price)
                        })
                    }
                }
            } catch (e: Exception) {
                error(e)
            }
        }
    }

    fun postOrder(uid: String, price: Quotation) {
        viewModelScope.launch {
            try {
                val accountsResult = repository.getAccounts()
                val accountsErrorBody = accountsResult.errorBody()
                val accountsBody = accountsResult.body()
                when {
                    // Error
                    accountsErrorBody != null -> {
                        error(accountsErrorBody)
                    }
                    // Success
                    accountsBody != null -> {
                        val orderResult = repository.postOrder(accountsBody.accounts.first().id, uid, price)
                        val orderErrorBody = orderResult.errorBody()
                        val orderBody = orderResult.body()
                        when {
                            // Error
                            orderErrorBody != null -> {
                                error(orderErrorBody)
                            }
                            // Success
                            orderBody != null -> {
                                _uiState.update { currentState ->
                                    currentState.copy(orderResult = "Заявка на покупку отправлена")
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                error(e)
            }
        }
    }

    fun hideOrderResult() {
        _uiState.update { currentState ->
            currentState.copy(orderResult = null)
        }
    }

    private fun loading() {
        _uiState.update { currentState ->
            currentState.copy(loading = true, error = null)
        }
    }

    private fun content(items: List<Item>) {
        _uiState.update { currentState ->
            currentState.copy(items = items, loading = false, error = null)
        }
    }

    private fun error(throwable: Throwable) {
        _uiState.update { currentState ->
            currentState.copy(items = emptyList(), loading = false, error = throwable)
        }
    }

    private fun error(responseBody: ResponseBody) {
        error(RuntimeException("error = ${responseBody.string()}"))
    }
}