package com.kursatkumsuz.circularchart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kursatkumsuz.circularchart.model.ChartModel
import com.kursatkumsuz.circularchart.ui.theme.CircularchartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircularchartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val chartData = listOf(
                        ChartModel(coin = "Arbitrum", amountPrice = 17521f, color = Color(0xFF54CE13),),
                        ChartModel(coin = "Optimism", amountPrice = 11256f, color = Color.Yellow),
                        ChartModel(coin = "Mina", amountPrice = 15472f, color = Color.LightGray),
                        ChartModel(coin = "Uniswap", amountPrice = 7824f, color = Color(0xFFE61298)),
                    )
                    CircularChart(chartData = chartData)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CircularchartTheme {
    }
}