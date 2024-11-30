@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.apisampleapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apisampleapp.ui.model.Item

@Composable
fun InvestScreen(investViewModel: InvestViewModel = viewModel()) {
    val investUiState by investViewModel.uiState.collectAsState()

    Column {
        TextField(
            value = investUiState.query,
            onValueChange = { investViewModel.onQueryChanged(it) },
            label = { Text("Введите название инструмента") }
        )
        Button(
            onClick = { investViewModel.findInstrument() },
            enabled = investUiState.query.isNotBlank() && investUiState.loading == false
        ) {
            Text(text = "Поиск")
        }
        Button(
            onClick = { investViewModel.getLastPrices() },
            enabled = investUiState.items.isNotEmpty()
        ) {
            Text(text = "Запросить цены")
        }
        Loading(investUiState.loading)
        Error(investUiState.error)
        Content(items = investUiState.items, investViewModel)
    }
    if (investUiState.orderResult != null) {
        investViewModel.hideOrderResult()
        Toast.makeText(LocalContext.current, investUiState.orderResult, Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun Content(items: List<Item>, investViewModel: InvestViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            InstrumentCard(item, investViewModel)
        }
    }
}

@Composable
private fun InstrumentCard(item: Item, investViewModel: InvestViewModel) {
    Card(
        onClick = {
            if (item.price != null) {
                investViewModel.postOrder(item.uid, item.price)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = item.subTitle,
                style = MaterialTheme.typography.bodyMedium
            )
            if (item.price != null) {
                Text(
                    text = "${item.price.units}.${item.price.nano}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun Loading(loading: Boolean?) {
    if (loading == true) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Error(throwable: Throwable?) {
    if (throwable != null) {
        Text(text = throwable.message ?: "Request error")
    }
}