package com.kursatkumsuz.circularchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.circularchart.model.ChartModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private fun Float.toAngle(totalValue: Float): Float {
    return this * 360 / totalValue
}


@Composable
fun CircularChart(chartData: List<ChartModel>) {

    val totalBalance = chartData.sumOf { it.amountPrice.toDouble() }.toFloat()
    val btcBalance = totalBalance / 42000

    val angle = remember {
        Animatable(0f)
    }

    val outerArcGap = remember {
        Animatable(0f)
    }

    val innerArcGap = remember {
        Animatable(0f)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentAlignment = Alignment.Center
        ) {
            LaunchedEffect(key1 = Unit) {
                launch {
                    angle.animateTo(360f, animationSpec = tween(2500))
                    outerArcGap.animateTo(2f, animationSpec = tween(1000))
                }
                launch {
                    delay(3750)
                    innerArcGap.animateTo(15f, animationSpec = tween(1000))
                }
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
            ) {
                val width = size.width
                val radius = width / 2f
                // Arc Sizes
                val outerArcSize = size / 1.1f
                val innerArcSize = size / 1.5f
                //Stroke Widths
                val outerArcStrokeWidth = 30.dp.toPx()
                val innerArcStrokeWidth = 2.dp.toPx()
                // Calculate circumference
                val circumference = 2 * Math.PI * radius
                // Calculate stroke angle
                val outerArcStrokeAngle = 0.7f * (outerArcStrokeWidth / circumference * 360f).toFloat()
                val innerArcStrokeAngle = 0.7f * (innerArcStrokeWidth / circumference * 360f).toFloat()
                //Angles
                var startAngle = -90f
                val inlineSweepAngle = 15f

                drawCircle(
                    brush = SolidColor(Color(0xFF333232)),
                    radius = radius / 1.1f,
                    style = Stroke(170f)
                )

                (0..24).forEach { _ ->
                    rotate(angle.value) {
                        drawArc(
                            color = Color(0xFF38354B),
                            startAngle = startAngle,
                            sweepAngle = inlineSweepAngle - innerArcStrokeAngle * innerArcGap.value,
                            useCenter = false,
                            topLeft = Offset(
                                (size.width / 2) - (innerArcSize.width / 2),
                                (size.height / 2) - (innerArcSize.height / 2),
                            ),
                            size = innerArcSize,
                            style = Stroke(innerArcStrokeWidth, cap = StrokeCap.Round)
                        )
                    }
                    startAngle += inlineSweepAngle
                }

                chartData.forEach { data ->
                    val sweepAngle = data.amountPrice.toAngle(totalValue = totalBalance)
                    rotate(angle.value) {
                        drawArc(
                            color = data.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle - outerArcStrokeAngle * outerArcGap.value,
                            useCenter = false,
                            topLeft = Offset(
                                (size.width / 2) - (outerArcSize.width / 2),
                                (size.height / 2) - (outerArcSize.height / 2),
                            ),
                            size = outerArcSize,
                            style = Stroke(outerArcStrokeWidth, cap = StrokeCap.Round)
                        )
                    }
                    startAngle += sweepAngle
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Balance", color = Color.Gray)
                Text(text = " $totalBalance$", color = Color.White, fontSize = 28.sp)
                Text(text = "$btcBalance BTC", color = Color.Gray)
            }
        }
        PortfolioList(coinList = chartData, totalBalance = totalBalance)
    }
}