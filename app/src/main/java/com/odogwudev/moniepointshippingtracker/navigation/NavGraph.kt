package com.odogwudev.moniepointshippingtracker.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.odogwudev.moniepointshippingtracker.ui.calculate.CalculateShipmentScreen
import com.odogwudev.moniepointshippingtracker.ui.calculate.EstimatedAmountScreen
import com.odogwudev.moniepointshippingtracker.ui.home.HomeScreen
import com.odogwudev.moniepointshippingtracker.ui.profile.ProfileScreen
import com.odogwudev.moniepointshippingtracker.ui.search.SearchScreen
import com.odogwudev.moniepointshippingtracker.ui.shipment.ShipmentHistoryScreen
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    // SharedTransitionLayout wraps the content to enable shared element transitions
    SharedTransitionLayout(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Home.route,
            modifier = modifier
        ) {
            val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = {
                fadeIn(animationSpec = tween(1500)) + slideInVertically(animationSpec = tween(800))
            }

            val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = {
                fadeOut(animationSpec = tween(1500)) + slideOutVertically(animationSpec = tween(800))
            }

            val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = {
                fadeIn(animationSpec = tween(1500)) + slideInVertically(animationSpec = tween(800))
            }

            val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = {
                fadeOut(animationSpec = tween(1500)) + slideOutVertically(animationSpec = tween(800))
            }

            // Home Screen
            composable(NavRoutes.Home.route,
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                HomeScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onNavigateToCalculateShipment = { navController.navigate(NavRoutes.CalculateShipment.route) },
                    onNavigateToShipmentHistory = { navController.navigate(NavRoutes.ShipmentHistory.route) },
                    onNavigateToProfile = { navController.navigate(NavRoutes.Profile.route) },
                    onNavigateToSearchShipment = { navController.navigate(NavRoutes.SearchShipment.route) }
                )
            }

            // Calculate Shipment
            composable(NavRoutes.CalculateShipment.route,
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                CalculateShipmentScreen(
                    onBack = { navController.navigateUp() },
                    onNavigateToEstimatedAmount = { navController.navigate(NavRoutes.EstimatedAmount.route) }
                )
            }

            // Estimated Amount
            composable(NavRoutes.EstimatedAmount.route,
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                EstimatedAmountScreen(
                    onBackToHome = { navController.popBackStack(NavRoutes.Home.route, false) }
                )
            }

            // Shipment History
            composable(NavRoutes.ShipmentHistory.route,
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                ShipmentHistoryScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // Profile Screen
            composable(NavRoutes.Profile.route,
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                ProfileScreen(
                    onBack = { navController.navigateUp() }
                )
            }

            // Search Shipment
            composable(NavRoutes.SearchShipment.route,
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                SearchScreen(
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}