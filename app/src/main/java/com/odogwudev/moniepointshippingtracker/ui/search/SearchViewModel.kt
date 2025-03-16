package com.odogwudev.moniepointshippingtracker.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.listOf

class SearchViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set


    private val shippingFlow = flowOf(
        listOf(
            ShippingItem(
                itemName = "MacBook Pro M4",
                orderNumber = "#NE43857340857904",
                fromDestination = "Paris",
                toDestination = "Barcelona"
            ), ShippingItem(
                itemName = "MacBook Pro M2",
                orderNumber = "#NE43857340857905",
                fromDestination = "Colombia",
                toDestination = "Bogota"
            ), ShippingItem(
                itemName = "iPhone 14 Pro",
                orderNumber = "#NE43857340857906",
                fromDestination = "London",
                toDestination = "New York"
            )
        )
    )

    val searchResults: StateFlow<List<ShippingItem>> =
        snapshotFlow { searchQuery }
            .combine(shippingFlow) { searchQuery, movies ->
                when {
                    searchQuery.isNotEmpty() -> movies.filter { movie ->
                        movie.itemName.contains(searchQuery, ignoreCase = true)
                    }
                    else -> movies
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )


    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }
}