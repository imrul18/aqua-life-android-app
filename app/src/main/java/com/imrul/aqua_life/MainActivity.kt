package com.imrul.aqua_life

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.imrul.aqua_life.dataset.DrinkDataModel
import com.imrul.aqua_life.navigation.graphs.RootGraph
import com.imrul.aqua_life.ui.theme.AquaLifeTheme
import com.imrul.aqua_life.ui.theme.Background

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AquaLifeTheme() {
                RootGraph()
            }
        }
    }
}
