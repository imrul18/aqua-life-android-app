package com.imrul.aqua_life.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.aqua_life.R
import com.imrul.aqua_life.ui.theme.Primary
import com.imrul.aqua_life.ui.theme.blue100
import com.imrul.aqua_life.ui.theme.blue200
import com.imrul.aqua_life.ui.theme.blue50
import com.imrul.aqua_life.ui.theme.water100
import com.imrul.aqua_life.ui.theme.water200
import com.imrul.aqua_life.ui.theme.water300
import kotlinx.coroutines.delay

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String = "Submit",
    loadingText: String = "Please wait...",
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    var gradientColors by remember { mutableStateOf(listOf(Primary, Primary)) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            val colorList = listOf(
                blue50,
                blue100,
                blue200,
                water100,
                water300,
                blue200,
                water200,
                blue50
            )
            gradientColors = colorList

            while (isLoading) {
                delay(250) // Wait for 0.5 seconds
                gradientColors = gradientColors.toMutableList().apply {
                    add(0, removeAt(size - 1))
                }
            }
        } else {
            gradientColors = listOf(Primary, Primary)
        }
    }


    Box(
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 30.dp)
            .fillMaxWidth()
            .background(if (isLoading) Color.White else Primary, RoundedCornerShape(8.dp))
            .drawBehind {
                val strokeWidth = 5.dp.toPx()
                val gradientBrush = Brush.sweepGradient(
                    colors = gradientColors,
                )

                drawRoundRect(
                    brush = gradientBrush,
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(strokeWidth)
                )
            }
            .padding(12.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if(!isLoading) {
                            onClick()
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = if (isLoading) Primary else Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = if (isLoading) loadingText else text
        )
    }
}