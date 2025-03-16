package com.odogwudev.moniepointshippingtracker.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.odogwudev.moniepointshippingtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit
) {
    val viewModel: SearchViewModel = viewModel()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { }, backgroundColor = Color(0xFF6A1B9A) // Purple color
                )
                PurpleSearchTopBar(
                    searchQuery = viewModel.searchQuery,
                    onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                    onBack = onBack
                )

            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (searchResults.isEmpty()) {
                ShippingEmptyState()
            } else {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    elevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(
                            count = searchResults.size,
                            key = { index -> searchResults[index].orderNumber },
                        ) { index ->
                            val item = searchResults[index]
                            ShippingItemRow(item)
                            val notLastItem = index < searchResults.size - 1
                            val moreThanOneItem = searchResults.size > 1
                            if (notLastItem && moreThanOneItem) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color.Gray.copy(alpha = 0.2f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShippingEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No Order found", style = MaterialTheme.typography.h4
        )
        Text(
            text = "Try adjusting your search", style = MaterialTheme.typography.body1
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurpleSearchTopBar(
    searchQuery: String, onSearchQueryChange: (String) -> Unit, onBack: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6A1B9A)) // Or use your own purple color
                .height(80.dp)
                .padding(end = 16.dp)

        ) {
            // Leading icon: back arrow
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(Modifier.width(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(shape = RoundedCornerShape(100.dp), color = Color.White)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color(0xFF6A1B9A),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(text = "Enter the receipt number ...", color = Color.Gray)
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color(0xFF6A1B9A),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color(0xFFFF9800), shape = CircleShape), // deep orange
                    contentAlignment = Alignment.Center
                ) {
                    // You can use any scan-related icon, e.g. "CameraAlt"
                    androidx.compose.material3.Icon(
                        painter = painterResource(R.drawable.noun_scan_7619571),
                        contentDescription = "Scan Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(Modifier.width(16.dp))

        }
        Spacer(Modifier.height(16.dp))
    }
}

data class ShippingItem(
    val itemName: String,
    val orderNumber: String,
    val fromDestination: String,
    val toDestination: String
)

@Composable
fun ShippingItemRow(item: ShippingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF6A1B9A)), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.shipbox),
                contentDescription = "Shipping Icon",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = item.itemName,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.orderNumber,
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = " · ${item.fromDestination} ",
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${item.toDestination}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}