package com.imrul.aqua_life.screens.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.imrul.aqua_life.R
import com.imrul.aqua_life.dataset.DrinkData
import com.imrul.aqua_life.dataset.DrinkDataModel
import com.imrul.aqua_life.ui.theme.Background
import com.imrul.aqua_life.ui.theme.KaffeeDark
import com.imrul.aqua_life.ui.theme.LimonadeDark
import com.imrul.aqua_life.ui.theme.Primary
import com.imrul.aqua_life.ui.theme.PrimaryLite
import com.imrul.aqua_life.ui.theme.SaftDark
import com.imrul.aqua_life.ui.theme.TeeDark
import com.imrul.aqua_life.ui.theme.WasserDark
import formatTimestamp

@Composable
fun History(
    navController: NavController,
    innerPadding: PaddingValues
) {
    val viewModel: DrinkDataModel = viewModel()

    val data by viewModel.drinkListForPie.observeAsState()
    val slices = data?.takeIf { it.isNotEmpty() }?.map { drink ->
        PieChartData.Slice(drink.label, drink.value, color = viewModel.getBeverageTypeDetails(drink.label).color)
    }
    val donutChartData = PieChartData(
        plotType = PlotType.Donut,
        slices = slices ?: listOf(PieChartData.Slice("Test", 1f, Color.White))
    )
    val donutChartConfig = PieChartConfig(
        backgroundColor = Color.White,
        activeSliceAlpha = 0.9f,
        strokeWidth = 70f,
        labelVisible = true,
        labelColor = Color.Black,
        labelFontSize = 20.sp,
        isAnimationEnable = true
    )

    val results by viewModel.drinkListForLineChart().observeAsState(emptyList())
    var pointsData by remember { mutableStateOf(listOf(0f))}

    LaunchedEffect(results) {
        var list = results.toMutableList()
        if(list.isNotEmpty()) {
            pointsData = list
        }
    }

    val listData by viewModel.drinkListForList.observeAsState(emptyList())

    Scaffold {innerPadding ->
        if (listData.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Keine Eintragungen",
                    color = Primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    modifier = Modifier.padding(top = 60.dp),
                    text = "Starte deinen Tag mit einem Glas Wasser!",
                    color = Primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .background(Background)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Ausertungen",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "TRINKVERHALTEN HEUTE",
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                        ) {
//                            CustomLineChart(pointsData = pointsData)
                            LineChartWithAxis(data = pointsData)
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DonutPieChart(
                                modifier = Modifier
                                    .padding(vertical = 15.dp, horizontal = 10.dp)
                                    .width(140.dp)
                                    .height(140.dp)
                                    .background(Color.White)
                                ,
                                donutChartData,
                                donutChartConfig
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp, bottom = 5.dp, top = 5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        data?.map {
                            Box(
                                modifier = Modifier
                                    .padding(end = 2.dp)
                                    .size(15.dp)
                                    .background(
                                        when (it.label) {
                                            "WASSER" -> WasserDark
                                            "KAFFEE" -> KaffeeDark
                                            "TEE" -> TeeDark
                                            "SAFT" -> SaftDark
                                            "LIMONADE" -> LimonadeDark
                                            else -> Color.Black
                                        }, CircleShape
                                    )
                            )
                            Text(
                                modifier = Modifier
                                .padding(end = 10.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                text = it.label,
                            )
                        }
                    }
                    Box(
                        Modifier.padding(start = 40.dp, top = 16.dp)
                    ) {
                        Text(
                            text = "EINTRAGUNGEN HEUTE",
                            color = Color.Gray,
                            fontSize = 14.sp,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .background(Color.White, RoundedCornerShape(16.dp))
                        ) {
                            listData.let {
                                LazyColumn(
                                    content = {
                                        itemsIndexed(it) { index: Int, item: DrinkData ->
                                            DrinkItemList(item, viewModel)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrinkItemList(item : DrinkData, model: DrinkDataModel = viewModel()) {
    val details = model.getBeverageTypeDetails(item.type.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
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
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.amount.toInt().toString() + " ml",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = details.label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = details.color
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = formatTimestamp(item.timestamp),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.width(290.dp)
                )
            }
        }
    }
}

@Composable
fun CustomLineChart(
    pointsData: List<Point> = listOf(Point(0f, 0f, "")),
) {
    var dataPoints = pointsData.map { it.y }
    println("HistoryPage CustomLineChart: $dataPoints")
    dataPoints = listOf(0f)
    println("HistoryPage CustomLineChart: $dataPoints")

    LineChartWithAxis(
        data = dataPoints,
    )
}

@Composable
fun LineChartWithAxis(
    data: List<Float>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .padding(top = 20.dp)
        .height(120.dp),
) {
    val maxValue = 100f

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.End,
        color = Color.Gray,
        fontSize = 14.sp,
        text = "% des Trinkziels",
    )
    Canvas(modifier = modifier) {
        val spaceBetweenPoints = size.width / (data.size - 1)
        val chartHeight = size.height // Reserve space for labels
        val chartWidth = size.width

        val gridLineCount = 4
        for (i in 0..gridLineCount) {
            val y = chartHeight * (1 - i / gridLineCount.toFloat())
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, y),
                end = Offset(chartWidth, y),
                strokeWidth = 1.dp.toPx()
            )

            drawContext.canvas.nativeCanvas.apply {
                // Calculate the x position for the labels on the right side
                val textX = chartWidth + 10f // Adjust this value based on text size and padding
                drawText(
                    "${(i * (maxValue / gridLineCount)).toInt()}", // Label text
                    textX,  // X position for the text (right side)
                    y + 2.dp.toPx(),  // Y position for the text
                    android.graphics.Paint().apply {
                        textSize = 30f
                        color = Color.Black.toArgb()
                        textAlign = android.graphics.Paint.Align.LEFT // Align text to the right
                    }
                )
            }
        }

        val path = Path()
        path.moveTo(0f, chartHeight - (data.first() / maxValue) * chartHeight)

        for (i in 0 until data.size - 1) {
            var originalValue = data[i]
            var originalValueNext = data[i + 1]
            if (data[i] > 100) {
                originalValue = 100f
            }

            if (data[i + 1] > 100) {
                originalValueNext = 100f
            }

            val startX = spaceBetweenPoints * i
            val startY = chartHeight - (originalValue / maxValue) * chartHeight

            val endX = spaceBetweenPoints * if (originalValue == originalValueNext) i else i + 1
            val endY = chartHeight - (originalValueNext / maxValue) * chartHeight

            val controlPoint1X = (startX + endX) / 2
            val controlPoint1Y = startY

            val controlPoint2X = (startX + endX) / 2
            val controlPoint2Y = endY

            // Create a smooth curve between two points using cubic Bézier curves
            path.cubicTo(
                controlPoint1X, controlPoint1Y,
                controlPoint2X, controlPoint2Y,
                endX, endY
            )
        }

        drawPath(
            path = path,
            brush = Brush.linearGradient(
                colors = listOf(Background, Primary), // Gradient colors
                start = Offset(0f, 0f),                  // Start of the gradient
                end = Offset(size.width, size.height)    // End of the gradient
            ),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Draw circles at data points with stroke and fill (similar to the image)
        data.forEachIndexed { index, value ->
            val x = spaceBetweenPoints * index
            val originalValue = if (value > 100) 100f else value
            val y = chartHeight - (originalValue / maxValue) * chartHeight

            // Draw outer circle (stroke effect)
            drawCircle(
                color = Primary, // Border color
                radius = 2.5.dp.toPx(), // Outer circle radius
                center = Offset(x, y),
                style = Stroke(width = 4.dp.toPx()) // Border thickness
            )

            // Draw inner circle (fill color)
            drawCircle(
                color = Color.White, // Inner circle color
                radius = 2.dp.toPx(), // Inner circle radius
                center = Offset(x, y)
            )
        }

        listOf(1, 2, 3, 4, 5, 6, 7).forEachIndexed { index, value ->
            val x = size.width / 6 * index
            if (value != 7) {
                drawLine(
                    color = Color.Gray, // Color of the dashed line
                    start = Offset(x, 0f), // Start at the top of the chart
                    end = Offset(x, chartHeight), // End at the bottom of the chart
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(10f, 10f),
                        0f
                    ) // Dash pattern
                )
            }
        }
    }
}
