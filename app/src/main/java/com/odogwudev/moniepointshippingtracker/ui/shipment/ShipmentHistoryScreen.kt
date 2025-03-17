package com.odogwudev.moniepointshippingtracker.ui.shipment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.odogwudev.moniepointshippingtracker.R
import com.odogwudev.moniepointshippingtracker.ui.search.SearchViewModel
import kotlinx.coroutines.launch

enum class ShipmentStatus {
    COMPLETED, IN_PROGRESS, PENDING, CANCELLED
}

data class Shipment(
    val status: ShipmentStatus,
    val arrivalTime: String,   // e.g. "Arriving today", "Arriving tomorrow"
    val orderNumber: String,   // e.g. "#NE43857340857904"
    val receivingFrom: String, // e.g. "Chicago"
    val price: Double,         // e.g. 1300.0
    val date: String           // e.g. "Sep 13 2025"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentHistoryScreen(
    viewModel: ShipmentHistoryViewModel = viewModel(),
    onBack: () -> Unit
) {
    val shipments by viewModel.filteredShipments.collectAsState()
    val statusCounts by viewModel.statusCounts.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()
    val tabItems = viewModel.tabItems
    val pagerState = rememberPagerState(pageCount = { tabItems.size })
    val scope = rememberCoroutineScope()

    var isContentVisible by remember { mutableStateOf(false) }
    var isCustomRowVisible by remember { mutableStateOf(false) }


    LaunchedEffect("") {
        isContentVisible = true
        isCustomRowVisible = true
    }


    LaunchedEffect(pagerState.currentPage) {
        viewModel.onStatusSelected(tabItems[pagerState.currentPage].first)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Shipments",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6A1B9A) // Purple
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = isCustomRowVisible,
                    enter = slideInHorizontally(
                        initialOffsetX = { w -> w },
                        animationSpec = tween(durationMillis = 800)) + fadeIn(
                        tween(800),
                    ),
                    exit = slideOutHorizontally(tween(durationMillis = 800)) + fadeOut(tween(800))
                ) {
                    ScrollableTabRow(
                        edgePadding = 0.dp,
                        selectedTabIndex = pagerState.currentPage,
                        backgroundColor = Color(0xFF6A1B9A),
                        contentColor = Color.White
                    ) {
                        tabItems.forEachIndexed { index, pair ->
                            val (status, tabTitle) = pair
                            val count = statusCounts.getOrElse(index) { 0 }
                            val isSelected = (pagerState.currentPage == index)

                            Tab(
                                selected = isSelected,
                                onClick = {
                                    scope.launch { pagerState.animateScrollToPage(index) }
                                    viewModel.onStatusSelected(status)
                                },
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .background(
                                                    color = if (isSelected) Color(0xFFFF9800) else Color(
                                                        0xFFE1BEE7
                                                    ),
                                                    shape = CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = count.toString(),
                                                fontSize = 12.sp,
                                                color = if (isSelected) Color.White else Color(
                                                    0xFF6A1B9A
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = tabTitle,
                                            color = if (isSelected) Color.White else Color(
                                                0xFFE1BEE7
                                            ),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = isContentVisible,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
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
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        val (status, _) = tabItems[page]
                        val filtered = shipments.filter { it.status == status || status == null }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            itemsIndexed(filtered) { index, shipment ->
                                ShipmentCard(shipment)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }

            // Fading effect at the bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.8f),
                                Color.White
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun ShipmentCard(shipment: Shipment) {
    Card(
        shape = RoundedCornerShape(12.dp), elevation = 1.dp, modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val (statusColor, statusIcon) = when (shipment.status) {
                    ShipmentStatus.IN_PROGRESS -> Pair(
                        Color(0xFF4CAF50), R.drawable.success
                    ) // green
                    ShipmentStatus.PENDING -> Pair(
                        Color(0xFFFFC107), R.drawable.pending
                    )       // yellow
                    ShipmentStatus.COMPLETED -> Pair(
                        Color(0xFF2196F3), R.drawable.loading
                    )    // blue
                    ShipmentStatus.CANCELLED -> Pair(
                        Color.Gray, R.drawable.cancelled
                    )            // gray
                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(statusColor.copy(alpha = 0.2f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(statusIcon),
                        contentDescription = "Status Icon",
                        tint = statusColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = shipment.status.name.replace("_", " "),
                    color = statusColor,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // ROW 2: Arrival info, order number, receiving from, shipping icon on the right
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.fillMaxWidth(.7f)) {
                    Text(
                        text = shipment.arrivalTime,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your delivery ${shipment.orderNumber} from ${shipment.receivingFrom}, is ${shipment.arrivalTime}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.shipmenthistory5), // your vector or image
                    contentDescription = "Shipping Illustration",
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${shipment.price} USD",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF6A1B9A) // purple
                )
                Text(
                    text = " · ${shipment.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}