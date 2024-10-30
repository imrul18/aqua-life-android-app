package com.imrul.aqua_life.screens.auth.profileSetup

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.imrul.aqua_life.dataset.ProfileData
import com.imrul.aqua_life.dataset.ProfileDataModel
import com.imrul.aqua_life.screens.main.profile.TextInput
import com.imrul.aqua_life.ui.theme.Primary
import kotlin.math.roundToInt

@Composable
fun TabTwo(
    data : ProfileData,
    setData : (ProfileData) -> Unit,
){
    val profilModel : ProfileDataModel = viewModel()

    val jobActivityList = profilModel.getJobActivityList()
    val sportsActivityList = profilModel.getSportsActivityList()
    val bottleWaterTypeList = profilModel.getBottleWaterTypeList()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 40.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Primary,
                text = "Meine Aktivität"
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                text = "In meinem Job verübe ich überwiegend:"
            )
            LazyRow(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(jobActivityList) { index, item ->
                    Box(
                        modifier = Modifier
                            .padding(start = 0.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                            .background(
                                color = if (data.jobActivity == item) Primary else Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                setData(data.copy(jobActivity = item))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = profilModel.getGermanJobActivity(item),
                            color = if (data.jobActivity == item) Color.White else Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                text = "Folgendes Fitnesslevel trifft auf mich zu:"
            )
            LazyRow(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(sportsActivityList) { index, item ->
                    Box(
                        modifier = Modifier
                            .padding(start = 0.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                            .background(
                                color = if (data.sportsActivity == item) Primary else Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                setData(data.copy(sportsActivity = item))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = profilModel.getGermanSportsActivity(item),
                            color = if (data.sportsActivity == item) Color.White else Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                text = "Meine Art der Wasserfilterung:"
            )
            LazyRow(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(bottleWaterTypeList) { index, item ->
                    Box(
                        modifier = Modifier
                            .padding(start = 0.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                            .background(
                                color = if (data.bottleWaterType == item) Primary else Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                setData(data.copy(bottleWaterType = item))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = profilModel.getGermanBottleWaterType(item),
                            color = if (data.bottleWaterType == item) Color.White else Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
