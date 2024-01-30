package com.kursatkumsuz.circularchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kursatkumsuz.circularchart.model.ChartModel
import kotlinx.coroutines.delay

private fun Float.toPercentage(totalValue: Float): Float {
    return this / totalValue * 100
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PortfolioList(coinList: List<ChartModel>, totalBalance: Float) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(coinList.size) { index ->
            ListItem(coin = coinList[index], totalBalance = totalBalance)
        }
    }
}

@Composable
fun ListItem(coin: ChartModel, totalBalance: Float) {

    val progressValue = coin.amountPrice.toPercentage(totalValue = totalBalance)

    val progress = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = progress) {
        delay(4500)
        progress.animateTo(targetValue = progressValue, animationSpec = tween(2000))
    }

    Box(
        modifier = Modifier
            .padding(25.dp)
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier
                .size(width = 130.dp, height = 80.dp)
                .background(Color(0xFF262A35)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = coin.coin, color = Color(0xFF868EB8))
            LinearProgressIndicator(
                color = coin.color,
                modifier = Modifier.padding(horizontal = 10.dp),
                trackColor = Color(0xFF434C64),
                progress = progress.value / 100f
            )
            Text(
                text = " ${"%.2f".format(progress.value)} % ", color = Color.White
            )

        }
    }
}