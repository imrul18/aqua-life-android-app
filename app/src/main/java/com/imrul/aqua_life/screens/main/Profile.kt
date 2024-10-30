package com.imrul.aqua_life.screens.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.imrul.aqua_life.R
import com.imrul.aqua_life.dataset.ProfileData
import com.imrul.aqua_life.dataset.ProfileDataModel
import com.imrul.aqua_life.screens.main.profile.DropDown
import com.imrul.aqua_life.screens.main.profile.TextInput
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Profile(
    navController: NavController,
    innerPadding: PaddingValues
) {
    val viewModel: ProfileDataModel = viewModel()
    val user by viewModel.profileData().asLiveData().observeAsState()

    var data by remember {mutableStateOf(user ?: ProfileData())}

    LaunchedEffect(user) {
        data = user ?: ProfileData()
    }

    var loading by remember { mutableStateOf(false) }
    val buttonText = if (loading) "Loading" else "Speichern"


    val sexList = viewModel.getSexList()
    val jobActivityList = viewModel.getJobActivityList()
    val sportsActivityList = viewModel.getSportsActivityList()
    val waterTypeList = viewModel.getBottleWaterTypeList()

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold() { innerPaddingValues ->
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .background(Background)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                item {
//                    Text(
//                        text = "Profil",
//                        modifier = Modifier.padding(top = 8.dp),
//                        fontSize = 24.sp,
//                        color = Color.Black,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
                item {
                    Text(
                        text = "Dein aktuelles Trinkziel",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 16.sp,
                        color = Primary,
                        fontWeight = FontWeight.Medium
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${String.format("%.2f", data.drinkGoalBurdenInLiters)} L",
                            color = Primary,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Image(
                            painter = painterResource(id = R.drawable.info_outline_24),
                            contentDescription = "Water Drop",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .height(30.dp)
                                .clickable {
                                    showBottomSheet = true
                                }
                        )
                    }
                }
                item {
                    Text(
                        text = "DEINE DATEN",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, top = 8.dp)
                        ,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        Column {
                            TextInput(
                                label = "Vorname",
                                text = data.firstname ?: "",
                                onChangeText = { selectedItem ->
                                    data = data.copy(firstname = selectedItem)
                                },
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            TextInput(
                                label = "Nachname",
                                text = data.surname ?: "",
                                onChangeText = { selectedItem ->
                                    data = data.copy(surname = selectedItem)
                                },
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            DropDown(
                                label = "Geschlecht",
                                selectedText = data.sex ?: "",
                                onItemSelected = { selectedItem ->
                                    data = data.copy(sex = selectedItem)
                                },
                                items = sexList.map { item -> item to viewModel.getGermanSex(item)},
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            TextInput(
                                label = "Größe",
                                text = data.height ?: "",
                                onChangeText = { selectedItem ->
                                    try {
                                        val floatValue = selectedItem.toFloat()
                                        data = data.copy(height = selectedItem)
                                    } catch (e: NumberFormatException) {
                                        println("Haha :Error: '$selectedItem' cannot be converted to a float")
                                    }
                                },
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            TextInput(
                                label = "Gewicht",
                                text = data.weight ?: "",
                                onChangeText = { selectedItem ->
                                    try {
                                        val floatValue = selectedItem.toFloat()
                                        data = data.copy(weight = selectedItem)
                                        data = data.copy(drinkGoalBurdenInLiters = viewModel.getDrinkGoalBurdenInLiters(data))
                                    } catch (e: NumberFormatException) {
                                        println("Haha :Error: '$selectedItem' cannot be converted to a float")
                                    }
                                },
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            DropDown(
                                label = "Beruf",
                                selectedText = data.jobActivity ?: "",
                                onItemSelected = { selectedItem ->
                                    data = data.copy(jobActivity = selectedItem)
                                    data = data.copy(drinkGoalBurdenInLiters = viewModel.getDrinkGoalBurdenInLiters(data))
                                },
                                items = jobActivityList.map { item -> item to viewModel.getGermanJobActivity(item)},
                                labelWidth = 0.2f,
                                valueWidth = 1f
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            DropDown(
                                label = "Aktivitätslevel",
                                selectedText = data.sportsActivity ?: "",
                                onItemSelected = { selectedItem ->
                                    data = data.copy(sportsActivity = selectedItem)
                                    data = data.copy(drinkGoalBurdenInLiters = viewModel.getDrinkGoalBurdenInLiters(data))
                                },
                                items = sportsActivityList.map { item -> item to viewModel.getGermanSportsActivity(item)},
                                labelWidth = 0.4f,
                                valueWidth = 1f
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            DropDown(
                                label = "Gefiltertes Wasser",
                                selectedText = data.bottleWaterType ?: "",
                                onItemSelected = { selectedItem ->
                                    data = data.copy(bottleWaterType = selectedItem)
                                    data = data.copy(drinkGoalBurdenInLiters = viewModel.getDrinkGoalBurdenInLiters(data))
                                },
                                items = waterTypeList.map { item -> item to viewModel.getGermanBottleWaterType(item)},
                                labelWidth = 0.5f,
                                valueWidth = 1f
                            )

                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Column(modifier = Modifier
                                .background(Color.White)
                                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                                .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Icon(Icons.Filled.CheckCircle, "contentDescription", tint = Color(0xFF54C759))
                                Text(
                                    text = "Da du dein Wasser filtrierst, hat sich dein Trinkziel um 10% verbessert.",
                                    fontSize = 14.sp,
                                    color = Primary,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Column(modifier = Modifier
                                .background(Color.White)
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable() {
                                    viewModel.updateProfileData(data)
                                },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = buttonText,
                                    modifier = Modifier
                                        .padding()
                                        .clickable() {
                                            if (!loading) {
                                                loading = true
                                                CoroutineScope(Dispatchers.Main).launch {
                                                    try {
                                                        viewModel.updateProfileData(data)
                                                        println("Error updating data:updated")
                                                    } catch (e: Exception) {
                                                        println("Error updating data: ${e.localizedMessage}")
                                                    } finally {
                                                        println("Error updating data:final")
                                                        loading = false
                                                    }
                                                }
                                            }
                                        },
                                    fontSize = 20.sp,
                                    color = Primary
                                )
                            }
                        }
                        ProfileInfo(
                            showBottomSheet = showBottomSheet,
                            onClick = {
                                showBottomSheet = false
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfo(
    showBottomSheet: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val url = "https://www1.aqua-global.com/?pid=1000120&nav_id=1000109"
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onClick,
            containerColor = Background,
        ) {
            Column(
                modifier = modifier
                    .background(Background)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Wir ermitteln dein tägliches Trinkziel, indem wir deinen Grundbedarf stets mit der Wertigkeit der von dir konsumierten Getränke verrechnen.",
                    color = Primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )

                Row(
                    modifier = Modifier.padding(top = 32.dp).clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                ) {
                    Icon(Icons.Filled.Search, "contentDescription", tint = Primary)
                    Text(
                        text = "Erfahre Mehr",
                        color = Primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 8.dp)
                        )
                }
            }
        }
                
    }
}
