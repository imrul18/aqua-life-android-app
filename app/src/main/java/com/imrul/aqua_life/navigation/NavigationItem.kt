package com.imrul.aqua_life.navigation
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val route : String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
)