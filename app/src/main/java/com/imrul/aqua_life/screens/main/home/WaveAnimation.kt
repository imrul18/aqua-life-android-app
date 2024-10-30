

package com.imrul.aqua_life.screens.main.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.imrul.aqua_life.dataset.DrinkDataModel
import kotlin.random.Random

@Composable
fun WaveAnimation(
    modifier: Modifier = Modifier,
    selectedDrink: String = "WASSER",
    waveHeight: Dp = 20.dp, // Mutable variable from parent
    waveLength: Dp = 100.dp,
    waveAmplitude: Dp = 10.dp,
    numberOfWaves: Int = 2,
    numberOfBubbles: Int = 50 // Increased number of bubbles
) {
    val infiniteTransition = rememberInfiniteTransition()
    var model = DrinkDataModel()
    var details = model.getBeverageTypeDetails(selectedDrink)

    // Animate the waveHeight whenever it changes
    val animatedWaveHeight = animateDpAsState(
        targetValue = waveHeight,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )

    // Create animations for multiple waves
    val waveOffsets = (0 until numberOfWaves).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = with(LocalDensity.current) { waveLength.toPx() },
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing, delayMillis = index * 200),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    val density = LocalDensity.current
    val waveHeightPx = with(density) { animatedWaveHeight.value.toPx() }
    val waveLengthPx = with(density) { waveLength.toPx() }
    val waveAmplitudePx = with(density) { waveAmplitude.toPx() }

    // Bubble animation
    val bubbles = remember { List(numberOfBubbles) { Bubble() } }
    val bubbleAnimations = bubbles.map { bubble ->
        infiniteTransition.animateFloat(
            initialValue = bubble.y,
            targetValue = -waveHeightPx + 350f, // Move bubbles above the screen
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = Random.nextInt(8000, 12000), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(modifier = modifier) {
        // Draw waves
        waveOffsets.forEachIndexed { index, waveOffsetState ->
            val waveOffset = waveOffsetState.value
            val currentWaveHeightPx = waveHeightPx
            val currentWaveAmplitudePx = waveAmplitudePx - (index * (waveAmplitudePx / numberOfWaves))
            val currentWaveLengthPx = waveLengthPx - (index * (waveLengthPx / numberOfWaves))

            val wavePath = Path().apply {
                moveTo(-currentWaveLengthPx + waveOffset, size.height - currentWaveHeightPx)
                var x = -currentWaveLengthPx + waveOffset
                while (x < size.width) {
                    relativeQuadraticBezierTo(
                        currentWaveLengthPx / 4, -currentWaveAmplitudePx,
                        currentWaveLengthPx / 2, 0f
                    )
                    relativeQuadraticBezierTo(
                        currentWaveLengthPx / 4, currentWaveAmplitudePx,
                        currentWaveLengthPx / 2, 0f
                    )
                    x += currentWaveLengthPx
                }
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = wavePath,
                brush = Brush.verticalGradient(
                    colors = details.gradient.map { it.copy(alpha = 0.7f) },
                    startY = size.height,
                    endY = size.height - currentWaveHeightPx
                )
            )
        }

        // Draw bubbles
        bubbles.forEachIndexed { index, bubble ->
            val bubbleY = bubbleAnimations[index].value

            // Only draw bubbles within screen bounds
            if (bubbleY < waveHeightPx) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.5f),
                    radius = bubble.size.toPx(),
                    center = androidx.compose.ui.geometry.Offset(
                        x = bubble.x.toPx(),
                        y = bubbleY
                    )
                )
            }
        }
    }
}

data class Bubble(
    val x: Dp = Random.nextInt(0, 1000).dp,
    val size: Dp = Random.nextInt(2, 4).dp, // Smaller size range
    val y: Float = Random.nextFloat()
)