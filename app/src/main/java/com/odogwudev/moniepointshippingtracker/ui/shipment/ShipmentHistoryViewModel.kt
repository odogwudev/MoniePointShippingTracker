package com.odogwudev.moniepointshippingtracker.ui.shipment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ShipmentHistoryViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    private val allShipments = listOf(
        Shipment(
            status = ShipmentStatus.IN_PROGRESS,
            arrivalTime = "Arriving today",
            orderNumber = "#NE43857340857904",
            receivingFrom = "Atlanta, GA",
            price = 1300.0,
            date = "Sep 13 2025"
        ),
        Shipment(
            status = ShipmentStatus.PENDING,
            arrivalTime = "Arriving tomorrow",
            orderNumber = "#NE43857340857905",
            receivingFrom = "New York, NY",
            price = 895.99,
            date = "Sep 14 2025"
        ),
        Shipment(
            status = ShipmentStatus.COMPLETED,
            arrivalTime = "Delivered",
            orderNumber = "#NE43857340857906",
            receivingFrom = "Boston, MA",
            price = 2200.0,
            date = "Sep 10 2025"
        ),
        Shipment(
            status = ShipmentStatus.CANCELLED,
            arrivalTime = "Cancelled en route",
            orderNumber = "#NE43857340857907",
            receivingFrom = "Denver, CO",
            price = 560.0,
            date = "Sep 12 2025"
        ),
        Shipment(
            status = ShipmentStatus.IN_PROGRESS,
            arrivalTime = "Arriving in 2 days",
            orderNumber = "#NE43857340857908",
            receivingFrom = "Los Angeles, CA",
            price = 1450.0,
            date = "Sep 15 2025"
        ),
        Shipment(
            status = ShipmentStatus.PENDING,
            arrivalTime = "Awaiting dispatch",
            orderNumber = "#NE43857340857909",
            receivingFrom = "Miami, FL",
            price = 760.0,
            date = "Sep 16 2025"
        ),
        Shipment(
            status = ShipmentStatus.COMPLETED,
            arrivalTime = "Delivered",
            orderNumber = "#NE43857340857910",
            receivingFrom = "Seattle, WA",
            price = 1890.0,
            date = "Sep 08 2025"
        ),
        Shipment(
            status = ShipmentStatus.CANCELLED,
            arrivalTime = "Cancelled before dispatch",
            orderNumber = "#NE43857340857911",
            receivingFrom = "Dallas, TX",
            price = 420.0,
            date = "Sep 17 2025"
        ),
        Shipment(
            status = ShipmentStatus.IN_PROGRESS,
            arrivalTime = "Arriving tomorrow",
            orderNumber = "#NE43857340857912",
            receivingFrom = "Chicago, IL",
            price = 1700.0,
            date = "Sep 18 2025"
        ),
        Shipment(
            status = ShipmentStatus.PENDING,
            arrivalTime = "Awaiting approval",
            orderNumber = "#NE43857340857913",
            receivingFrom = "San Francisco, CA",
            price = 980.0,
            date = "Sep 19 2025"
        ),
        Shipment(
            status = ShipmentStatus.COMPLETED,
            arrivalTime = "Delivered",
            orderNumber = "#NE43857340857914",
            receivingFrom = "Philadelphia, PA",
            price = 2050.0,
            date = "Sep 09 2025"
        ),
        Shipment(
            status = ShipmentStatus.CANCELLED,
            arrivalTime = "Cancelled in transit",
            orderNumber = "#NE43857340857915",
            receivingFrom = "Houston, TX",
            price = 600.0,
            date = "Sep 20 2025"
        )
    )

    private val _selectedStatus = MutableStateFlow<ShipmentStatus?>(null)
    val selectedStatus: StateFlow<ShipmentStatus?> = _selectedStatus.asStateFlow()

    val filteredShipments: StateFlow<List<Shipment>> =
        snapshotFlow { searchQuery }
            .combine(_selectedStatus) { searchQuery, selectedStatus ->
                allShipments.filter { shipment ->
                    (selectedStatus == null || shipment.status == selectedStatus) &&
                            (searchQuery.isEmpty() || shipment.orderNumber.contains(searchQuery, ignoreCase = true))
                }
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = allShipments,
                started = SharingStarted.WhileSubscribed(5_000)
            )

    val tabItems = listOf(
        null to "All",
        ShipmentStatus.COMPLETED to "Completed",
        ShipmentStatus.IN_PROGRESS to "In Progress",
        ShipmentStatus.PENDING to "Pending",
        ShipmentStatus.CANCELLED to "Cancelled"
    )

    val statusCounts: StateFlow<List<Int>> = flow {
        emit(tabItems.map { (status, _) ->
            if (status == null) allShipments.size else allShipments.count { it.status == status }
        })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun onStatusSelected(status: ShipmentStatus?) {
        _selectedStatus.value = status
    }
}