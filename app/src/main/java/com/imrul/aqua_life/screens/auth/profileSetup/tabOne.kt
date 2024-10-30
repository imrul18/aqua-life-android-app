package com.imrul.aqua_life.screens.auth.profileSetup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.aqua_life.dataset.ProfileData
import com.imrul.aqua_life.ui.theme.Primary
import kotlin.math.roundToInt


import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.imrul.aqua_life.R
import com.imrul.aqua_life.ui.theme.Link
import com.imrul.aqua_life.ui.theme.blue200

@Composable
fun TabOne(
    data : ProfileData,
    setData : (ProfileData) -> Unit,
){
    val scrollState = rememberScrollState()

    var error by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        style = TextStyle(
                            lineHeight = 36.sp // Adjust line height here
                        ),
                        color = Primary,
                        text = "Herzlich willkommen\nbei Aqua Life!"
                    )
                }
            }
            Row {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.edit), // Replace with your image resource
                            contentDescription = null,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            colorFilter = ColorFilter.tint(Link)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            textAlign = TextAlign.Center,
                            color = Link,
                            text = "Gib deinen Einladungs-Code ein"
                        )

                    }


                    Text(
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        text = "Lass uns Schritt für Schritt herausfinden, \nwie viel du für eine optimale Gesundheit\ntäglich trinken musst."
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        text = "Dein biologisches Geschlecht ist..."
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (data.sex == "female") {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        setData(data.copy(sex = "female"))
                                    }
                                    .width(120.dp)
                                    .background(Primary, RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium,
                                    text = "Weiblich"
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        setData(data.copy(sex = "female"))
                                    }
                                    .width(120.dp)
                                    .shadow(4.dp, shape = RoundedCornerShape(6.dp))
                                    .background(Color.White, RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    color = Primary,
                                    fontWeight = FontWeight.Medium,
                                    text = "Weiblich"
                                )
                            }
                        }
                        if (data.sex == "male") {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        setData(data.copy(sex = "male"))
                                    }
                                    .width(120.dp)
                                    .background(Primary, RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium,
                                    text = "Männlich"
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        setData(data.copy(sex = "male"))
                                    }
                                    .width(120.dp)
                                    .shadow(4.dp, shape = RoundedCornerShape(6.dp))
                                    .background(Color.White, RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    color = Primary,
                                    fontWeight = FontWeight.Medium,
                                    text = "Männlich"
                                )
                            }
                        }
                    }

                    Text(
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center,
                        text = "Dein Gewicht und deine Größe.."

                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        NumberInputField(
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(bottom = 16.dp)
                                .width(50.dp),
                            min = 40,
                            max = 200,
                            label = "Größe",
                            value = data.height,
                            onValueChange = { selectedItem ->
                                println("AfnanKhan $selectedItem")
                                setData(data.copy(height = selectedItem))
                            },
                            onError = { isError ->
                                error = isError
                            },
                            unit = "kg"
                        )
                        NumberInputField(
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(bottom = 16.dp)
                                .width(50.dp),
                            min = 120,
                            max = 250,
                            label = "Gewicht",
                            value = data.weight,
                            onValueChange = { selectedItem ->
                                setData(data.copy(weight = selectedItem))
                            },
                            onError = { isError ->
                                error = isError
                            },
                            unit = "cm"
                        )
                    }
                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NumberInputField(
    modifier: Modifier,
    label: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    onError: (String) -> Unit,
    min: Int = 0,
    max: Int = 10,
    unit: String = ""
) {
    var isError by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { inputValue ->
            onValueChange(inputValue)
            val intValue = inputValue.toIntOrNull()
            if (intValue in min..max) {
                onValueChange(inputValue)
                isError = false
                onError("")
            } else if (inputValue.isEmpty()) {
                isError = true
                onError("$label is required")
            } else {
                isError = true
                onError("$label should be between $min and $max $unit")
            }
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = if (isError) Color.Red else Color.Black
        ),
        modifier = Modifier
            .border(1.dp, if (isError) Color.Red else Color.Gray, RoundedCornerShape(4.dp))
            .padding(8.dp)
            .width(70.dp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                innerTextField()

            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
    Row(
        modifier = Modifier
            .padding(top = 4.dp)
            .padding(end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = unit,
            fontSize = 16.sp,
            color = Color.Black
        )
    }

}
