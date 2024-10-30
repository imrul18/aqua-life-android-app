package com.imrul.aqua_life.screens.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun TextInput(
    label: String,
    text: String,
    onChangeText: (String) -> Unit
) {

    var textfieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    Column(
        Modifier
            .background(Color.White)
            .padding(8.dp)) {
        Row (
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 4.dp, top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = label, Modifier.fillMaxWidth(.3f), color = Color.Gray)
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(.7f)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    }
                    .padding(0.dp),
            ) {
                Row(
                    Modifier.fillMaxWidth() ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    BasicTextField(
                            value = text,
                            onValueChange = onChangeText,
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth(),
                            textStyle = TextStyle(
                                textAlign = TextAlign.End,
                                fontSize = 16.sp,
                            ),
                        )
                }
            }
        }
    }
}
