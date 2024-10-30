package com.imrul.aqua_life.screens.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.imrul.aqua_life.R
import com.imrul.aqua_life.dataset.DrinkData
import com.imrul.aqua_life.dataset.DrinkDataModel
import com.imrul.aqua_life.dataset.DrinkDataTotal
import com.imrul.aqua_life.dataset.beverageType
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.Primary
import com.imrul.aqua_life.ui.theme.PrimaryLite
import com.imrul.aqua_life.ui.theme.Secondary
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import com.imrul.aqua_life.screens.main.home.NewsPortal
import com.imrul.aqua_life.ui.theme.BodyColor
import kotlin.math.pow
import kotlin.math.sqrt
import com.imrul.aqua_life.screens.main.home.TransparentModal
import com.imrul.aqua_life.screens.main.home.WaveAnimation
import java.util.UUID
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.input.pointer.PointerInputChange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    innerPadding: PaddingValues
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidthDp.toPx() }
    val screenHeightPx = with(LocalDensity.current) { screenHeightDp.toPx() }


    val viewModel: DrinkDataModel = viewModel()

    val data by viewModel.drinkListTotal().asLiveData().observeAsState()

    var drinkData by remember {mutableStateOf(data ?: DrinkDataTotal())}

    LaunchedEffect(data) {
        drinkData = data ?: DrinkDataTotal(
            quantity = 0f,
            points = 0
        )
    }

    val animatedQuantity by animateFloatAsState(targetValue = drinkData.quantity ?: 0f)
    var showBottomSheet by remember { mutableStateOf(false) }

    var dragRing by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var DragItem by remember { mutableStateOf(0) }

    var drinkListOffset by remember { mutableStateOf(
        listOf(
            Triple(arrayOf((screenWidthPx / 2).toInt() - 60 - 300, 370), arrayOf((screenWidthPx / 2).toInt() - 60 - 300, 370), arrayOf(R.drawable.glass200ml, 200)),  // -60 is the width of the image and +/-100 is the offset
            Triple(arrayOf((screenWidthPx / 2).toInt() - 60 - 220, 100), arrayOf((screenWidthPx / 2).toInt() - 60 - 220, 100), arrayOf(R.drawable.glass330ml, 330)),
            Triple(arrayOf((screenWidthPx / 2).toInt()- 60 + 220, 100), arrayOf((screenWidthPx / 2).toInt()- 60 + 220, 100), arrayOf(R.drawable.glass500ml, 500)),
            Triple(arrayOf((screenWidthPx / 2).toInt()- 60 + 300, 370), arrayOf((screenWidthPx / 2).toInt()- 60 + 300, 370), arrayOf(R.drawable.glass750ml, 750)),
            Triple(arrayOf((screenWidthPx * 0.1).toInt(), (screenHeightPx * .6).toInt()), arrayOf((screenWidthPx * 0.1).toInt(), (screenHeightPx * .6).toInt()), arrayOf(R.drawable.manual_add_drink_light, 0)),
            Triple(arrayOf((screenWidthPx * 0.9).toInt() - 130, (screenHeightPx * .6).toInt()), arrayOf((screenWidthPx * 0.9).toInt() - 130, (screenHeightPx * .6).toInt()), arrayOf(R.drawable.news_light, -5)),
        )
    ) }


    var selectedDrink by remember { mutableStateOf("WASSER") }
    var selectedDrinkID by remember { mutableStateOf<UUID?>(null) }

    var isCustom by remember { mutableStateOf(false) }
    var isCustomOk by remember { mutableStateOf(false) }

    var dragEnd: (Int, Boolean, Boolean ) -> Unit = {dragItem, isCustomItem, isTap ->
        dragRing = false
        if (isTap) {
            if(isCustomItem){
                isCustom = true
                isCustomOk = true
            } else {
                showDialog = true
            }
            DragItem = dragItem
            viewModel.addDrinkData(
                DrinkData(
                    amount = dragItem.toFloat(),
                    type = beverageType.valueOf("WASSER")
                )
            ) { id ->
                selectedDrinkID = id
            }
        } else {
            var item = drinkListOffset.find { it.third[1] == dragItem }
            item?.let {
                val centerX = screenWidthPx / 2
                val centerY = screenHeightPx / 3
                val radius = 200f

                val x = it.second[0].toFloat()
                val y = it.second[1].toFloat()

                val distance = sqrt((x - centerX).pow(2) + (y - centerY).pow(2))

                if (distance <= radius) {
                    if(isCustomItem){
                        isCustom = true
                        isCustomOk = true
                    } else {
                        showDialog = true
                    }
                    DragItem = dragItem
                    viewModel.addDrinkData(
                        DrinkData(
                            amount = dragItem.toFloat(),
                            type = beverageType.valueOf("WASSER")
                        )
                    ) { id ->
                        selectedDrinkID = id
                    }
                }
            }
            drinkListOffset = drinkListOffset.map {
                Triple(it.first, it.first, it.third)
            }
        }
    }

    var onChangeDrug: (Int, Offset) -> Unit = { item, amount ->
        var selectedItem = drinkListOffset.find { it.third[1] == item }
        var newOffset = drinkListOffset.map {
            if (it.third[1] == item) {
                Triple(it.first, arrayOf(it.second[0] + amount.x.toInt(), it.second[1] + amount.y.toInt()), it.third)
            } else {
                it
            }
        }
        drinkListOffset = newOffset
            val centerX = screenWidthPx / 2
            val centerY = screenWidthPx / 3
            val radius = 200f

            val x = selectedItem!!.second[0].toFloat()
            val y = selectedItem!!.second[1].toFloat()

            val distance = sqrt((x - centerX).pow(2) + (y - centerY).pow(2))


            if (distance <= radius) {
                dragRing = true
            } else {
                dragRing = false
            }
    }

    var clickPosition by remember { mutableStateOf(5000) }

    var start by remember { mutableStateOf(0) }
    var end by remember { mutableStateOf(0) }
    var selectCustomQuantity: (Float) -> Unit = { position ->
        if(isCustom) {
            clickPosition = position.toInt()
            val positionDelta = (clickPosition - start).toFloat()
            val totalDelta = (end - start).toFloat()
            val progressRatio = positionDelta / totalDelta
            val adjustedValue = 1000 - (progressRatio * 1000).toInt()
            DragItem = adjustedValue.coerceIn(0, 1000)

            val drinkDataId = selectedDrinkID
            if (drinkDataId != null) {
                println("DrinkDataId called: $drinkDataId")
                viewModel.updateDrinkDataAmountById(drinkDataId, DragItem.toFloat())
            }
        }
    }

    val animatedRadius by animateFloatAsState(
        targetValue = if (dragRing) 200f else 1f,
        animationSpec = tween(durationMillis = 500) // Adjust duration if needed
    )

    Scaffold(
        modifier = Modifier
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    selectCustomQuantity(change.position.y)
                }
            )
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                ),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.water_drop),
                                    contentDescription = "Water Drop",
                                    modifier = Modifier
                                        .height(20.dp)
                                )
                                Text(
                                    text = "${"%.0f".format(animatedQuantity)}%",
                                    color = Primary,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Image(
                            painter = painterResource(id = R.drawable.home_logo),
                            contentDescription = "Home Logo",
                        )
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 15.dp)
                            ) {
                                Text(
                                    text = "${drinkData.points}",
                                    color = Primary,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.trophy),
                                    contentDescription = "Water Drop",
                                    modifier = Modifier
                                        .height(20.dp)
                                )
                            }
                        }
                    }
                })
        },
    ) { innerPadding : PaddingValues ->
        Box(Modifier.background(BodyColor)) {
            WaveAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter),
                selectedDrink = selectedDrink,
                waveHeight = ((540*drinkData.quantity/100) + 110).dp,
                waveAmplitude = 10.dp,
            )
            Box(
                modifier = Modifier
                    .run {
                        padding(innerPadding)
                            .fillMaxHeight(0.9f)
                            .fillMaxWidth()
                            .paint(
                                painterResource(id = R.drawable.male_mask_cropped),
                                contentScale = ContentScale.FillHeight,
                                colorFilter = ColorFilter.tint(Background)
                            )
                    }
            )
        }
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp)
                    .padding(bottom = 100.dp)
                    .drawBehind {
                        val strokeWidth = 10.dp.toPx()
                        val y = size.height
                        drawLine(
                            color = Secondary,
                            start = Offset(1f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Square
                        )
                    }
                    .drawBehind {
                        val strokeWidth = 10.dp.toPx()
                        val y = size.height
                        val gradientBrush = Brush.linearGradient(
                            colors = listOf(Primary, PrimaryLite),
                            start = Offset.Zero,
                            end = Offset((size.width * animatedQuantity / 100), 0f),
                            tileMode = TileMode.Clamp
                        )
                        drawLine(
                            brush = gradientBrush,
                            start = Offset(1f, y),
                            end = Offset((size.width * animatedQuantity / 100), y),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NewsPortal(
                        showBottomSheet = showBottomSheet,
                        onClick = {
                            showBottomSheet = false
                        }
                    )

                }
            }

        if(isCustom){
            Box(
                modifier = Modifier
                    .fillMaxSize() // Fill the entire screen size
            ) {
                if(isCustomOk) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 10.dp)
                            .align(Alignment.CenterStart)
                    ) {
                        start = 400
                        end = size.height.toInt() - 550
                        for (i in start..end step 50) {
                            var width = if (i % 500 == 0) 30.dp.toPx() else 20.dp.toPx()
                            var color = if (i > clickPosition) Primary else Secondary
                            drawLine(
                                color = color,
                                start = Offset(x = 50f, y = i.toFloat()),
                                end = Offset(x = 50f, y = (i + 15).toFloat()),
                                strokeWidth = width
                            )
                        }
                    }
                }
            }
            TransparentModal(
                DragItem = DragItem,
                isCustomOk,
                setIsCutomOk = { isCustomOk = it },
                showDialog = true,
                onDismissRequest = {
                    isCustom = false
                    if(DragItem == 0 || isCustomOk) {
                        viewModel.deleteDrinkDataById(selectedDrinkID!!)
                    }
                    DragItem = 0
                    selectedDrink = "WASSER"
                    clickPosition = 5000
                },
                selectedDrink = selectedDrink,
                onChangeDrink = { selectedItem ->
                    selectedDrink = selectedItem
                    val currentDrinkID = selectedDrinkID
                    if (currentDrinkID != null) {
                        viewModel.updateDrinkDataTypeById(currentDrinkID, beverageType.valueOf(selectedItem))
                    }
                }
            )
        } else {
            if(!showDialog) {
                Box(
                    modifier = Modifier
                        .padding(top= screenHeightDp / 3.5f)
                        .fillMaxWidth()
                        .size(150.dp) // Adjust the size as needed
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        PrimaryLite,
                                        Color.Transparent
                                    ),
                                    radius = animatedRadius
                                ),
                                shape = CircleShape
                            ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.9f), shape = CircleShape)
                                .padding(horizontal = 15.dp),
                            text = "+",
                            fontSize = 40.sp,
                            color = Primary
                        )
                }
            }

            Box(modifier = Modifier
                .fillMaxSize()) {
                if (!showDialog) {
                    drinkListOffset.forEachIndexed { index, (_, varoffset, dragItem) ->
                        when (index) {
                            4 -> {
                                Image(
                                    painter = painterResource(id = dragItem[0]),
                                    contentDescription = "Water Drop",
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .offset { IntOffset(varoffset[0], varoffset[1]) }
                                        .height(150.dp)
                                        .width(60.dp)
                                        .pointerInput(Unit) {
                                            detectDragGestures(
                                                onDragEnd = {
                                                    dragEnd(dragItem[1], true, false)
                                                }
                                            ) { change, dragAmount ->
                                                change.consume()
                                                onChangeDrug(dragItem[1], dragAmount)
                                            }
                                        }
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    dragEnd(dragItem[1], true, true)
                                                }
                                            )
                                        }
                                )
                            }
                            5 -> {
                                Image(
                                    painter = painterResource(id = dragItem[0]),
                                    contentDescription = "Water Drop",
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .padding(top = 60.dp, start = 20.dp)
                                        .offset { IntOffset(varoffset[0], varoffset[1]) }
                                        .height(75.dp)
                                        .width(30.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    showBottomSheet = true
                                                }
                                            )
                                        }
                                )
                            }
                            else -> {
                                Image(
                                    painter = painterResource(id = dragItem[0]),
                                    contentDescription = "Water Drop",
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .offset { IntOffset(varoffset[0], varoffset[1]) }
                                        .height(120.dp)
                                        .width(50.dp)
                                        .pointerInput(Unit) {
                                            detectDragGestures(
                                                onDragEnd = {
                                                    dragEnd(dragItem[1], false, false)
                                                }
                                            ) { change, dragAmount ->
                                                change.consume()
                                                onChangeDrug(dragItem[1], dragAmount)
                                            }
                                        }
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = {
                                                    dragEnd(dragItem[1], false, true)
                                                }
                                            )
                                        }
                                )
                            }
                        }
                    }
                }
                TransparentModal(
                    DragItem = DragItem,
                    showDialog = showDialog,
                    onDismissRequest = {
                        showDialog = false
                        selectedDrink = "WASSER"
                    },
                    selectedDrink = selectedDrink,
                    onChangeDrink = { selectedItem ->
                        selectedDrink = selectedItem
                        val currentDrinkID = selectedDrinkID
                        if (currentDrinkID != null) {
                            viewModel.updateDrinkDataTypeById(currentDrinkID, beverageType.valueOf(selectedItem))
                        }
                    }
                )
            }


//            Image(
//                painter = painterResource(id = if (isSystemInDarkTheme()) {
//                    R.drawable.manual_add_drink_dark
//                } else {
//                    R.drawable.manual_add_drink_light
//                }),
//                contentDescription = "Water Drop",
//                modifier = Modifier
//                    .offset { IntOffset((screenWidthPx * 0.1).toInt(), (screenHeightPx * 0.75).toInt()) }
//                    .height(100.dp)
//                    .pointerInput(Unit) {
//                        detectDragGestures(
//                            onDragEnd = {
//                                val centerX = screenWidthPx / 2f
//                                val centerY = screenHeightPx / 3.5f
//                                val radius = 200f
//
//                                val x = (screenWidthPx * 0.1).toFloat()
//                                val y = (screenHeightPx * 0.75).toFloat()
//
//                                val distance = sqrt((x - centerX).pow(2) + (y - centerY).pow(2))
//
//                                if (distance <= radius) {
//                                    showDialog = true
//                                    viewModel.addDrinkData(
//                                        DrinkData(
//                                            amount = 0f,
//                                            type = beverageType.valueOf("WASSER")
//                                        )
//                                    ) { id ->
//                                        selectedDrinkID = id
//                                    }
//                                }
//                            }
//                        ) { change, dragAmount ->
//                            change.consume()
////                            onChangeDrug(dragItem[1], dragAmount)
//                        }
//                    }
//                    .clickable {
//                        viewModel.addDrinkData(
//                            DrinkData(
//                                amount = 0f,
//                                type = beverageType.valueOf("WASSER")
//                            )
//                        ) { id ->
//                            selectedDrinkID = id
//                        }
//                        isCustom = true
//                        isCustomOk = true
//                    }
//            )
        }

//        Image(
//            painter = painterResource(id = R.drawable.news_light),
//            contentDescription = "Water Drop",
//            modifier = Modifier
//                .offset {
//                    IntOffset(
//                        (screenWidthPx * 0.9).toInt(),
//                        (screenHeightPx * 0.8).toInt()
//                    )
//                }
//                .offset(x = (-50).dp)
//                .background(
//                    color = Secondary,
//                    shape = CircleShape
//                )
//                .padding(15.dp)
//                .clickable {
//                    showBottomSheet = true
//                }
//        )
    }
}
