package com.odogwudev.moniepointshippingtracker.ui.calculate

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.odogwudev.moniepointshippingtracker.R
import kotlinx.coroutines.delay

@Composable
fun EstimatedAmountScreen(
    onBackToHome: () -> Unit
) {
    // We'll animate from 1000 to 1460 by incrementing digit-by-digit with a short delay
    var animatedAmount by remember { mutableStateOf(1000) }

    // Once the screen is composed, we start the animation
    LaunchedEffect(Unit) {
        for (value in 1000..1460) {
            animatedAmount = value
            delay(5)
        }
    }

    // UI layout
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1) First row: "MoveMate" + shipping icon tinted yellow
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MoveMate",
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF6A1B9A),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.noun_shipping),
                    contentDescription = "Shipping Icon",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFFFFC107)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.shipmenthistory5), // your vector or image
                contentDescription = "Shipping Illustration",
                modifier = Modifier.size(200.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total Estimated Amount",
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$$animatedAmount USD",
                style = MaterialTheme.typography.h6,
                color = Color(0xFF4CAF50) // Green
            )


            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This amount is estimated this will vary if you change your location or weight",
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { onBackToHome() },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800) // Deep orange
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .height(56.dp)
            ) {
                androidx.compose.material3.Text(text = "Back To Home", color = Color.White)
            }
        }
    }
}