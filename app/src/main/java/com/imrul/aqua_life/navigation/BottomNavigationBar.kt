package com.imrul.aqua_life.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    currentRoute: String?,
    onClick: (NavigationItem) -> Unit,
) {

    NavigationBar(
        containerColor= Background
    ) {
            items.forEachIndexed { index, navigationItem ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Background
                    ),
                    selected = currentRoute == navigationItem.route,
                    onClick = { onClick(navigationItem) },
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (currentRoute == navigationItem.route) {
                                    navigationItem.selectedIcon
                                } else {
                                    navigationItem.unSelectedIcon
                                }
                            ),
                            contentDescription = navigationItem.route,
                            Modifier.size(30.dp)
                        )
                    }
                )
            }

    }
} 