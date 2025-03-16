package com.odogwudev.moniepointshippingtracker.ui.calculate

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.odogwudev.moniepointshippingtracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateShipmentScreen(
    onBack: () -> Unit,
    onNavigateToEstimatedAmount: () -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Calculate Shipment", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Or your custom back icon
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6A1B9A) // Purple color
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Destination",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.Start)
            )
            DestinationCard()

            Spacer(modifier = Modifier.height(24.dp))

            // 3) Packaging section
            Text(
                text = "Packaging",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "What are you sending",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            PackagingRow()

            Spacer(modifier = Modifier.height(24.dp))

            // 4) Categories section
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "What are you sending",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            CategoriesSection()

            Spacer(modifier = Modifier.weight(1f))

            // 5) Calculate button
            Button(
                onClick = { onNavigateToEstimatedAmount() },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800) // Deep orange
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .height(56.dp)
            ) {
                Text(text = "Calculate",  style = MaterialTheme.typography.titleMedium, color = Color.White)
            }
        }
    }
}

@Composable
fun DestinationCard() {
    var senderLocation by remember { mutableStateOf("") }
    var receiverLocation by remember { mutableStateOf("") }
    var approxWeight by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CustomTextFieldWithIconDivider(
                value = senderLocation,
                onValueChange = { senderLocation = it },
                placeholder = "Sender location",
                leadingIcon = R.drawable.noun_send
            )
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextFieldWithIconDivider(
                value = receiverLocation,
                onValueChange = { receiverLocation = it },
                placeholder = "Receiver location",
                leadingIcon = R.drawable.noun_receive
            )
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextFieldWithIconDivider(
                value = approxWeight,
                onValueChange = { approxWeight = it },
                placeholder = "Approx. weight",
                leadingIcon = R.drawable.approx_weight
            )
        }
    }
}

/**
 * A reusable text field composable with a leading icon + vertical divider + text field
 */
@Composable
fun CustomTextFieldWithIconDivider(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Int,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(3.dp))
                // Vertical divider
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(Color.Gray.copy(alpha = 0.5f))
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color(0xfffafafa),
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()}),
                placeholder = { Text(text = placeholder, color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun PackagingRow() {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.shipmenthistory5), // your vector or image
                contentDescription = "Shipping Illustration",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp)
                    .background(Color.Gray.copy(alpha = 0.5f))
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Box",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown"
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSection() {
    val categories = listOf(
        "Documents", "Glass", "Liquid", "Food",
        "Electronic", "Product", "Others"
    )

    // Track selected items
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    // We'll lay them out in a FlowRow-like approach
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            SelectableChip(
                text = category,
                isSelected = (selectedCategory == category),
                onClick = {
                    selectedCategory = if (selectedCategory == category) null else category
                }
            )
        }
    }
}

/**
 * A composable chip that toggles selected/unselected state with an animated size expansion
 * and color changes. If unselected: black border, white background, black text
 * If selected: black background, white text
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectableChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedSize by animateDpAsState(
        targetValue = if (isSelected) 44.dp else 40.dp,
        animationSpec = tween(durationMillis = 200)
    )

    // The shape
    val shape = RoundedCornerShape(20.dp)
    val borderColor = Color.Black
    val backgroundColor = if (isSelected) Color.Black else Color.White
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(animatedSize)
            .animateContentSize()
            .border(BorderStroke(1.dp, borderColor), shape)
            .background(backgroundColor, shape)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor
        )
    }
}
enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.70f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {  }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}