package com.imrul.aqua_life.screens.auth.profileSetup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.aqua_life.components.BubbleAnimation
import com.imrul.aqua_life.dataset.ProfileData
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Primary
import com.imrul.aqua_life.ui.theme.blue200
import com.imrul.aqua_life.ui.theme.water100
import com.imrul.aqua_life.ui.theme.water200

@Composable
fun StepHeader(data: ProfileData, currentTab: Int, total: Int = 2, onClick: (Int) -> Unit) {

    // Water bubbles background animation
    BubbleAnimation(bubbleCount = 50, speed = 0.5f, height = 0.25f)

    Column (
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to water200.copy(alpha = 0.3f),
                        0.1f to water200.copy(alpha = 0.6f),
                        0.3f to water100.copy(alpha = 0.8f),
                        0.6f to blue200.copy(alpha = 0.46f),
                        1.0f to blue200.copy(alpha = 0.7f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .padding(20.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            repeat(total) { index ->
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(4.dp)
                        .background(
                            color = if (index == currentTab) Primary else Background,
                            shape = CircleShape
                        )
                        .clickable { onClick(index) }
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                color = Color.White,
                text = "Dein tägliches Trinkziel:"
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                text = "${String.format("%.2f", data.drinkGoalBurdenInLiters)} L",
            )
        }
    }
}

