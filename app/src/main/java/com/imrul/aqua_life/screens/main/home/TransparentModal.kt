package com.imrul.aqua_life.screens.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.aqua_life.dataset.DrinkDataModel
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Primary

@Composable
fun TransparentModal(DragItem: Int, isCustomOk: Boolean = false, setIsCutomOk: (Boolean) -> Unit = {},showDialog: Boolean, onDismissRequest: () -> Unit, selectedDrink: String, onChangeDrink: (String) -> Unit){
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp

    val renderList = listOf("WASSER", "KAFFEE", "TEE", "SAFT", "LIMONADE")

    var showBottomSheet by remember { mutableStateOf(false) }

    val icon = if (showBottomSheet)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 0.dp, y = (screenHeightDp / 3).dp),
        ){
            Column(
                modifier = Modifier
                .fillMaxWidth(),
                ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            onDismissRequest()
                        }
                        .align(Alignment.CenterHorizontally)
                    ,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp)
                    )
                    Text(
                        color = Primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        text = "zurück"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column {
                        Text(
                            text = "$DragItem ml",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary
                        )
                    }
                }
                if (isCustomOk) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .border(1.dp, Background, RoundedCornerShape(20.dp))
                                .background(Background, shape = RoundedCornerShape(20.dp))
                                .clickable {
                                    setIsCutomOk(false)
                                }
                                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)

                        ) {
                            Text(
                                text = "Ok",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                            )
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,

                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .border(1.dp, Background, RoundedCornerShape(20.dp))
                                .background(Background, shape = RoundedCornerShape(20.dp))
                                .clickable {
                                    showBottomSheet = true
                                }
                                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)

                        ) {
                            Text(
                                text = selectedDrink,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                            )
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Background,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(16.dp)
                                    .border(1.dp, Background, RoundedCornerShape(20.dp))
                                    .background(Primary, shape = RoundedCornerShape(20.dp))
                            )
                        }

                        ComDropDown(
                            showBottomSheet = showBottomSheet,
                            setBottomSheet = { showBottomSheet = it },
                            selectedText = selectedDrink,
                            onChangeDrink = { onChangeDrink(it) },
                            items = renderList,
                            dragItem = DragItem
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun ComDropDown(
    showBottomSheet: Boolean,
    setBottomSheet: (Boolean) -> Unit,
    selectedText: String,
    onChangeDrink: (String) -> Unit,
    items: List<String>,
    dragItem: Int
) {
    DropDownList(
        showBottomSheet = showBottomSheet,
        onClick = {
            setBottomSheet(false)
        },
        selectedText = selectedText,
        onItemSelected = { selectedItem ->
            onChangeDrink(selectedItem)
        },
        items = items
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(
    showBottomSheet: Boolean,
    onClick: () -> Unit,
    selectedText: String,
    onItemSelected: (String) -> Unit,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    var model = DrinkDataModel()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onClick,
            containerColor = Background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items.forEach { item ->
                    val details = model.getBeverageTypeDetails(item)
                    Column {
                        Row(
                            modifier = Modifier
                                .background(Background)
                                .padding(top = 5.dp, bottom = 5.dp, start = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .clickable { onItemSelected(item) },
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                modifier = Modifier
                            ) {
                                Column(
                                    modifier = Modifier.padding(5.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = details.icon),
                                        contentDescription = "Login Image",
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(40.dp)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(start = 5.dp, top = 13.dp, end = 20.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = details.label,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.padding(5.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                if (selectedText == item) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = Background,
                                        modifier = Modifier
                                            .padding(start = 5.dp, top = 10.dp, end = 20.dp)
                                            .size(20.dp)
                                            .border(1.dp, Background, RoundedCornerShape(40.dp))
                                            .background(Primary, shape = RoundedCornerShape(40.dp))
                                            .padding(3.dp)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 5.dp, top = 10.dp, end = 20.dp)
                                            .size(20.dp)
                                            .border(1.dp, Primary, RoundedCornerShape(40.dp))
                                            .padding(3.dp)
                                    )
                                }
                            }
                        }
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                        )
                    }

//                    Column {
//                        Row(
//                            modifier = Modifier
//                                .padding(top = 5.dp, bottom = 5.dp, start = 20.dp, end = 20.dp)
//                                .fillMaxWidth(),
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(5.dp),
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Image(
//                                    painter = painterResource(id = details.icon),
//                                    contentDescription = "Login Image",
//                                    modifier = Modifier
//                                        .padding(end = 10.dp)
//                                        .size(40.dp)
//                                )
//                            }
//                            Column(
//                                modifier = Modifier.padding(5.dp),
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Text(
//                                    text = details.label,
//                                    fontSize = 14.sp,
//                                    fontWeight = FontWeight.Medium,
//                                    color = details.color
//                                )
//                            }
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize(),
//                                contentAlignment = Alignment.CenterEnd,
//                            ) {
//                                Column(
//                                    modifier = Modifier.padding(5.dp),
//                                ) {
//                                    Text(
//                                        text = "Afnan",
//                                        fontSize = 14.sp,
//                                        color = Color.Gray
//                                    )
//                                }
//                            }
//                        }
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.End
//                        ) {
//                            Divider(
//                                color = Color.LightGray,
//                                thickness = 1.dp,
//                                modifier = Modifier.width(290.dp)
//                            )
//                        }
//                    }
                }
            }
        }
    }
}