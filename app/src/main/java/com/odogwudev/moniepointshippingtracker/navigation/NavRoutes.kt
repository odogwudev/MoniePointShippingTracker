package com.odogwudev.moniepointshippingtracker.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object CalculateShipment : NavRoutes("calculate_shipment")
    object EstimatedAmount : NavRoutes("estimated_amount")
    object ShipmentHistory : NavRoutes("shipment_history")
    object Profile : NavRoutes("profile")
    object SearchShipment : NavRoutes("search_shipment")
}