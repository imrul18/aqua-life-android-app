package com.imrul.aqua_life.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicInputFieldWithIcon(
    backgroundColor: Color = Color.White,
    leftIcon: Int? = null,
    leftIconClick: () -> Unit = {},
    rightIcon: Int? = null,
    rightIconClick: () -> Unit = {},
    value: String = "",
    onValueChange: (value: String) -> Unit,
    placeholderText: String,
    showText: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current // Get the keyboard controller

    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // Set border radius here
            .border(2.dp, Color.Transparent, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .background(backgroundColor)
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leftIcon != null) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = leftIcon),
                    contentDescription = "login_person"
                )
            }
            Box {
                BasicTextField(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(0.9f),
                    value = value,
                    onValueChange = {newValue ->
                        if (newValue.isNotEmpty() && newValue.last() == '\n') {
                            keyboardController?.hide()
                        }
                        onValueChange(newValue.trimEnd())
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                    ),
                    visualTransformation = if (showText) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )
                if (value.isEmpty()) {
                    Text(
                        text = placeholderText,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            color = Color.Gray // Placeholder color
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }
            }
            if (rightIcon != null) {
                Image(
                    modifier = Modifier
                        .size(28.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    rightIconClick()
                                }
                            )
                        },
                    painter = painterResource(id = rightIcon),
                    contentDescription = "login_person"
                )
            }
        }
    }
}
