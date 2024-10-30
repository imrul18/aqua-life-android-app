package com.imrul.aqua_life.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.imrul.aqua_life.ui.theme.water200
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun BubbleAnimation(bubbleCount: Int = 30, speed: Float = 1f, height: Float = 1f) {
    Box(modifier = Modifier.fillMaxSize()) {
        Bubbles(modifier = Modifier.fillMaxSize(), bubbleCount = bubbleCount, speed = speed, height = height)
    }
}

@Composable
fun Bubbles(modifier: Modifier, bubbleCount: Int, speed: Float, height: Float) {
    val bubbles = remember { List(bubbleCount) { Bubble() } }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // Convert screen dimensions from dp to px
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }

    // Draw all bubbles inside the single Canvas
    Canvas(modifier = modifier) {
        bubbles.forEach { bubble ->
            drawCircle(
                color = water200.copy(alpha = bubble.alpha),
                radius = bubble.size,
                center = Offset(bubble.x, bubble.y)
            )
        }
    }

    // Launch animations for each bubble
    bubbles.forEach { bubble ->
        LaunchedEffect(bubble) {
            while (true) {
                // Reset bubble position and animate it
                bubble.reset(screenWidth, screenHeight, height)
                animateBubble(bubble, speed)
                delay(2000L) // Delay for continuous animation
            }
        }
    }
}

suspend fun animateBubble(bubble: Bubble, speed: Float) {
    val durationMillis = (bubble.duration / speed).toInt()
    val startY = bubble.y
    val endY = bubble.targetY

    var currentTime = 0
    while (currentTime < durationMillis) {
        val progress = currentTime.toFloat() / durationMillis
        bubble.y = startY + progress * (endY - startY)
        currentTime += 16 // Assuming a 60fps frame rate
        delay(16L) // Wait for the next frame
    }

    bubble.y = endY
}

class Bubble {
    var x: Float by mutableStateOf(0f)
    var y: Float by mutableStateOf(0f)
    var size: Float by mutableStateOf(0f)
    var targetY: Float by mutableStateOf(0f)
    var duration: Float by mutableStateOf(0f)
    var alpha: Float by mutableStateOf(1f)

    init {
        reset(1080f, 1920f) // Defaults, these will be overridden dynamically
    }

    fun reset(screenWidth: Float, screenHeight: Float, height: Float = 1f) {
        x = Random.nextFloat() * screenWidth // Random X position based on actual screen width
        size = Random.nextInt(5, 15).toFloat() // Bubble size (random radius)
        y = screenHeight * height // Start from the bottom (screen height + size)
        targetY = -size // Move to the top
        duration = Random.nextFloat() * 3000f + 2000f // Random speed
        alpha = Random.nextFloat() * 0.5f + 0.5f // Random transparency
    }
}
