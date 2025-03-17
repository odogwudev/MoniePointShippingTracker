package com.odogwudev.moniepointshippingtracker.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.odogwudev.moniepointshippingtracker.R
import com.odogwudev.moniepointshippingtracker.navigation.NavRoutes
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToSearchShipment: () -> Unit,
    onNavigateToCalculateShipment: () -> Unit,
    onNavigateToShipmentHistory: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val trackingItems =
        listOf(
            TrackingInfo(
                shipmentNumber = "#NE43857340857904",
                sender = "Atlanta, 5243",
                receiver = "Chicago, 6342",
                time = "2-3 days",
                status = "In Transit"
            )

        )
    var isListVisible by remember { mutableStateOf(false) }

    val vehicles = remember {
        listOf(
            VehicleInfo(
                title = "Ocean Freight",
                description = "International",
                illustrationRes = R.drawable.cargo_freight
            ),
            VehicleInfo(
                title = "Cargo Freight",
                description = "Reliable",
                illustrationRes = R.drawable.cargo
            ),
            VehicleInfo(
                title = "Air Freight",
                description = "International",
                illustrationRes = R.drawable.airplane
            )
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { PurpleTopBar(onSearchClicked = onNavigateToSearchShipment) },
        bottomBar = {
            BottomNavBar(
                onHomeClicked = {},
                onNavigateToShipmentHistory = onNavigateToShipmentHistory,
                onCalculateShipmentClicked = onNavigateToCalculateShipment,
                onProfileClicked = onNavigateToProfile
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "Tracking",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            itemsIndexed(trackingItems) { index, item ->
                TrackingCard(info = item)
            }

            item {
                Text(
                    text = "Available Vehicles",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                AnimatedVisibility(
                    visible = isListVisible,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                    ) + fadeIn(
                        initialAlpha = 0f,
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                    ) + fadeOut(
                        targetAlpha = 0f,
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                    ),
                ) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        itemsIndexed (vehicles) { index, vehicle ->
                            VehicleCard(vehicle, sharedTransitionScope, animatedVisibilityScope)
                        }
                    }
                }
            }
        }
        LaunchedEffect("") {
            isListVisible = true
        }
    }
}

@Composable
fun BottomNavBar(
    onHomeClicked: () -> Unit,
    onCalculateShipmentClicked: () -> Unit,
    onNavigateToShipmentHistory: () -> Unit,
    onProfileClicked: () -> Unit
) {
    var selectedItem by remember { mutableStateOf<NavRoutes>(NavRoutes.Home) }
    // This controls if we’re currently showing the “selected” state (for 2 seconds)
    var showSelectedEffect by remember { mutableStateOf(false) }

    // We can watch for changes to `selectedItem` and trigger the effect
    LaunchedEffect(selectedItem) {
        if (showSelectedEffect) {
            // Wait
            delay(100)
            // Then navigate
            when (selectedItem) {
                NavRoutes.Home -> { /* Already on home; no nav */
                }

                NavRoutes.CalculateShipment -> onCalculateShipmentClicked()
                NavRoutes.Profile -> onProfileClicked()
                NavRoutes.ShipmentHistory -> onNavigateToShipmentHistory()
                NavRoutes.SearchShipment -> Unit
                NavRoutes.EstimatedAmount -> Unit
            }
            showSelectedEffect = false
        }
    }

    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // 1) Home item
        BottomNavigationItem(selected = selectedItem == NavRoutes.Home, onClick = {
            selectedItem = NavRoutes.Home
            showSelectedEffect = true
        }, icon = {
            NavItemIconWithBlueLine(
                label = "Home", isSelected = selectedItem == NavRoutes.Home
            )
        })

        // 2) Calculate Shipment item
        BottomNavigationItem(selected = selectedItem == NavRoutes.CalculateShipment, onClick = {
            selectedItem = NavRoutes.CalculateShipment
            showSelectedEffect = true
        }, icon = {
            NavItemIconWithBlueLine(
                label = "Calculate", isSelected = selectedItem == NavRoutes.CalculateShipment
            )
        })

        BottomNavigationItem(selected = selectedItem == NavRoutes.ShipmentHistory, onClick = {
            selectedItem = NavRoutes.ShipmentHistory
            showSelectedEffect = true
        }, icon = {
            NavItemIconWithBlueLine(
                label = "Shipment", isSelected = selectedItem == NavRoutes.ShipmentHistory
            )
        })

        // 3) Profile item
        BottomNavigationItem(selected = selectedItem == NavRoutes.Profile, onClick = {
            selectedItem = NavRoutes.Profile
            showSelectedEffect = true
        }, icon = {
            NavItemIconWithBlueLine(
                label = "Profile", isSelected = selectedItem == NavRoutes.Profile
            )
        })
    }
}


@Composable
fun NavItemIconWithBlueLine(label: String, isSelected: Boolean) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .widthIn(min = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show a small blue line if selected
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color(0xFF6A1B9A), shape = RoundedCornerShape(2.dp))
            )
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Example icon or text label
        Icon(
            imageVector = Icons.Default.Home, contentDescription = label
        )
        Text(text = label)
    }
}


@Composable
fun PurpleTopBar(
    onSearchClicked: () -> Unit
) {
    // Main container with purple background
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6A1B9A)) // Or use your own purple color
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Spacer(Modifier.height(30.dp))

        // --------------------------
        // Row 1: Profile & Location & Notification
        // --------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Left side: Profile icon + (Location info)
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Profile icon
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile icon",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Your location", color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Weirtheim, Illnois", color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Dropdown",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100.dp))
                .clickable {
                    onSearchClicked()
                }
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Enter the receipt number", color = Color.Gray
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color(0xFFFF9800), shape = CircleShape), // deep orange
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.noun_scan_7619571),
                    contentDescription = "Scan Icon",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun TrackingCard(info: TrackingInfo, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp), elevation = 1.dp, modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Column {
                    Text(
                        text = "Shipment Number",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = info.shipmentNumber,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.forklift), // your vector or image
                    contentDescription = "Shipping Illustration", modifier = Modifier.size(48.dp)
                )
            }

            Divider(color = Color.LightGray.copy(alpha = 0.5f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(   Modifier.weight(1f),verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFFFF3E0), CircleShape), // very light orange
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sendbox_12920324),
                            contentDescription = "Shipping Icon",
                            tint = Color(0xFFFF9800), // medium orange
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Sender",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = info.sender,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
                Row(   Modifier.weight(1f),verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Green, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Time",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = info.time,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

            // Row 3: receiver info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(   Modifier.weight(1f),verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFE0F2F1), CircleShape), // light greenish
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.receievebox_2352225),
                            contentDescription = "Shipping Icon",
                            tint = Color(0xFF009688), // teal-ish
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Receiver",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = info.receiver,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }

                // Right side: status
                Column(   Modifier.weight(1f).padding(start = 8.dp),horizontalAlignment = Alignment.Start) {

                    Text(
                        text = "Status",
                        color = Color.Gray,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = info.status,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "+",
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add Stop",
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
@Composable
fun VehicleCard(
    vehicleInfo: VehicleInfo, sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        // Define bounds transform for shared element transition
        val boundsTransform = BoundsTransform { initialBounds, targetBounds ->
            keyframes {
                durationMillis = 1000
                initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                targetBounds at 1000
            }
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 3.dp,
            modifier = Modifier.size(width = 200.dp, height = 200.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = vehicleInfo.title,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = vehicleInfo.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Image(
                    painter = painterResource(id = vehicleInfo.illustrationRes),
                    contentDescription = vehicleInfo.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

data class TrackingInfo(
    val shipmentNumber: String,
    val sender: String,
    val receiver: String,
    val time: String,
    val status: String
)

data class VehicleInfo(
    val title: String, val description: String, val illustrationRes: Int
)
