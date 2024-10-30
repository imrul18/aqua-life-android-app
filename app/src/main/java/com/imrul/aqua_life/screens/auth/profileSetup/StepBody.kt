package com.imrul.aqua_life.screens.auth.profileSetup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.imrul.aqua_life.dataset.ProfileData

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StepBody (
    data : ProfileData,
    setData : (ProfileData) -> Unit,
    mainCurrentTab : Int = 0,
    setMainCurrentTab : (Int) -> Unit,
) {
    var dragAmountState = 0f

    var currentTab by remember { mutableStateOf(mainCurrentTab) }

    println("currentTab: $currentTab")
    var previousTab by remember { mutableStateOf(0) }
    LaunchedEffect(currentTab) {
        setMainCurrentTab(currentTab)
        previousTab = currentTab
    }

    LaunchedEffect(mainCurrentTab) {
        currentTab = mainCurrentTab
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume() // Consume the gesture to prevent it from being used elsewhere
                        dragAmountState = dragAmount // Update the drag direction
                    },
                    onDragEnd = {
                        if (dragAmountState > 0) {
                            if (currentTab > 0) {
                                currentTab--
                            }
                        } else {
                            if (currentTab < 1) {
                                currentTab++
                            }
                        }
                    }
                )
            },
    ) {
        AnimatedContent(
            targetState = currentTab,
            transitionSpec = {
                if (currentTab > previousTab) {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }
                    ) + fadeIn() with slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }
                    ) + fadeOut()
                } else {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ) + fadeIn() with slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }
                    ) + fadeOut()
                }.using(SizeTransform(clip = false))
            }

        ) { targetTab ->
            when (targetTab) {
                0 -> TabOne(
                    data = data,
                    setData = { setData(it) },
                )
                1 -> TabTwo(
                    data = data,
                    setData = { setData(it) },
                )
            }
        }
    }
}