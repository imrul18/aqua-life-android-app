package com.imrul.aqua_life.screens.main.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Primary

data class BoxItem(
    val imageUrl: String,
    val url: String = "",
    val title: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsPortal(
    showBottomSheet: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BoxItem("https://images.pexels.com/photos/989959/pexels-photo-989959.jpeg?cs=srgb&dl=pexels-aleksandr-slobodianyk-367235-989959.jpg&fm=jpg", "https://www2.aqua-global.com/wasserbar", "Warum Aqua Global", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
        BoxItem("https://images.pexels.com/photos/989959/pexels-photo-989959.jpeg?cs=srgb&dl=pexels-aleksandr-slobodianyk-367235-989959.jpg&fm=jpg", "https://www2.aqua-global.com/wasserbar", "Warum Aqua Global", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
        BoxItem("https://images.pexels.com/photos/989959/pexels-photo-989959.jpeg?cs=srgb&dl=pexels-aleksandr-slobodianyk-367235-989959.jpg&fm=jpg", "https://www2.aqua-global.com/wasserbar", "Warum Aqua Global", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
        BoxItem("https://images.pexels.com/photos/989959/pexels-photo-989959.jpeg?cs=srgb&dl=pexels-aleksandr-slobodianyk-367235-989959.jpg&fm=jpg", "https://www2.aqua-global.com/wasserbar", "Warum Aqua Global", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onClick,
            containerColor = Background,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(start = 16.dp, bottom = 32.dp),
                color = Primary,
                fontWeight = FontWeight.Bold,
                text = "Global News"
            )
            TwoColumnLayout(items)
        }
    }
}



@Composable
fun TwoColumnLayout(items: List<BoxItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    BoxItemView(item, Modifier.weight(1f))
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(.5f))
                }
            }
        }
    }
}

@Composable
fun BoxItemView(item: BoxItem, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
        ,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp).clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                        context.startActivity(intent)
                        },
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.title,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}