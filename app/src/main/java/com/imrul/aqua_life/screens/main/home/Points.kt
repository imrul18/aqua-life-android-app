package com.imrul.aqua_life.screens.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.imrul.aqua_life.R
import com.imrul.aqua_life.dataset.PointData
import com.imrul.aqua_life.navigation.MainScreenScreens
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Primary
import com.imrul.aqua_life.ui.theme.PrimaryLite
import com.imrul.aqua_life.ui.theme.Secondary

@Composable
fun Points(
    navController: NavController
) {
    val data = arrayOf(
        PointData(
            pointID = 1,
            id = "d56a6b6e-8e8a-4d88-91c7-9a55578a8b3a",
            thumbnail_url = "https://images.unsplash.com/photo-1501525547-7b008cf72da5?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            subtitle = "Erhalten Sie 30% Rabatt auf unsere hochwertigen Filter.",
            title = "30% Rabatt auf Filter",
            costInPoints = 300,
        ),
        PointData(
            pointID = 2,
            id = "d56a6b6e-8e8a-4d88-91c7-9a55578a8b3a",
            thumbnail_url = "https://images.unsplash.com/photo-1501525547-7b008cf72da5?q=80&w=3174&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            subtitle = "Erhalten Sie 30% Rabatt auf unsere hochwertigen Filter.",
            title = "30% Rabatt auf Filter",
            costInPoints = 300,
        )
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Secondary,
                                PrimaryLite
                            )
                        )
                    ),
            ) {
                    Row(
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    navController.navigate(MainScreenScreens.Home.route)
                                }
                            ),
                        horizontalArrangement = Arrangement.End
                    ) {
                            Text(
                                text = "X",
                                color = Color(0xFF222222)
                            )
                        }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 28.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Dein Punktestand",
                            style = TextStyle(
                                color = Primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "350",
                        style = TextStyle(
                            color = Primary,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.trophy),
                        contentDescription = "Water Drop",
                        modifier = Modifier
                            .height(64.dp)
                            .size(48.dp)
                    )
                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 140.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                data.forEach { item ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .border(
                            width = 2.dp,
                            color = Primary,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)) {
                        Box {
                            Row {
                                Column {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(item.thumbnail_url)
                                            .crossfade(true)
                                            .build(),
                                        placeholder = painterResource(R.drawable.home_logo),
                                        contentDescription = "Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(48.dp)
                                    )
                                }
                                Column(
                                    Modifier.padding(start = 16.dp)
                                ) {
                                    Text(
                                        text = item.title,
                                        style = TextStyle(
                                            color = Primary,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = item.subtitle,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                }
                            }
                        }
                        Divider(
                            color = Secondary,
                            thickness = 1.dp,
                            modifier = Modifier.padding(top = 64.dp)
                        )
                        Box(
                            Modifier
                                .padding(top = 68.dp)
                                .fillMaxWidth(),
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "EINLÖSEN für ${item.costInPoints}P",
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .clickable { print("Clicked") },
                                    style = TextStyle(
                                        color = Primary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}


