package com.imrul.aqua_life.screens.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun DropDown(
    label: String,
    items: List<Pair<String, String>>,
    selectedText: String,
    labelWidth: Float = .30f,
    valueWidth: Float = .70f,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var textfieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        Modifier
            .background(Color.White)
            .padding(10.dp)) {
        Row (
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 5.dp, top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                Modifier.fillMaxWidth(labelWidth),
                color = Color.Gray
            )

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(valueWidth)
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { expanded = !expanded }
                    .padding(0.dp),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                        Text(
                            text = items.find { it.first == selectedText }?.second ?: "",
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Icon(icon, "contentDescription")
                }
            }
        }
        Box(
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                .background(Color.White)
                .clip(RoundedCornerShape(20.dp)) // Apply border radius of 20.dp here
                .align(Alignment.End)
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .background(Color.White)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(item.first)
                            expanded = false
                        },
                        text = {
                            Column(
                                Modifier.padding(0.dp)
                            ) {
                                Row {
                                    if (selectedText == item.first) {
                                        Icon(Icons.Filled.Check, "contentDescription")
                                        Text(text = item.second)
                                    } else {
                                        Text(
                                            text = item.second,
                                            Modifier.padding(start = 24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                    if(item != items.last()) {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

            }
        }
    }
}
