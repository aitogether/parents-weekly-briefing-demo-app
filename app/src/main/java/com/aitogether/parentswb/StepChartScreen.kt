package com.aitogether.parentswb

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitogether.parentswb.ui.theme.*
import kotlin.random.Random

data class StepData(
    val day: String,
    val steps: Int
) {
    val color: Color
        get() = when {
            steps >= 3000 -> WarmGreen
            steps >= 1000 -> WarmHighlight
            else -> ScheduleRed
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepChartScreen(onBack: () -> Unit) {
    val mockData = remember {
        val days = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
        days.map { day -> StepData(day, Random.nextInt(800, 4501)) }
    }

    val avg = mockData.map { it.steps }.average().toInt()
    val max = mockData.maxOf { it.steps }
    val min = mockData.minOf { it.steps }

    val firstHalf = mockData.take(4).map { it.steps }.average()
    val secondHalf = mockData.drop(3).map { it.steps }.average()
    val trendText = when {
        secondHalf > firstHalf * 1.1 -> "📈 后半周步数有回升，状态不错！"
        secondHalf < firstHalf * 0.9 -> "📉 后半周步数有所下降，需要留意。"
        else -> "➡️ 本周步数整体比较平稳。"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("妈 · 步数趋势图", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandTeal,
                    titleContentColor = White,
                    navigationIconContentColor = White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 柱状图
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "本周每日步数",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    val maxSteps = max.coerceAtLeast(1)
                    val chartHeight = 200.dp

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(chartHeight)
                    ) {
                        val barWidth = size.width / (mockData.size * 2 + 1)
                        val spacing = barWidth
                        mockData.forEachIndexed { index, data ->
                            val barHeight = (data.steps.toFloat() / maxSteps) * size.height
                            val x = spacing + index * (barWidth + spacing)
                            val y = size.height - barHeight
                            drawRect(
                                color = data.color,
                                topLeft = Offset(x, y),
                                size = Size(barWidth, barHeight)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // X轴标签
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        mockData.forEach { data ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    data.steps.toString(),
                                    fontSize = 10.sp,
                                    color = MediumGray,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    data.day,
                                    fontSize = 12.sp,
                                    color = DarkGray,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 图例
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        LegendItem(color = WarmGreen, label = "≥3000 正常")
                        LegendItem(color = WarmHighlight, label = "1000-3000 留意")
                        LegendItem(color = ScheduleRed, label = "<1000 需关注")
                    }
                }
            }

            // 汇总卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BrandTealLight),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("📊 步数汇总", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SummaryItem("日均", avg.toString())
                        SummaryItem("最高", max.toString())
                        SummaryItem("最低", min.toString())
                    }
                }
            }

            // 趋势判断
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("趋势判断", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(trendText, fontSize = 14.sp, color = DarkGray, lineHeight = 22.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "前半周（周一至周三）日均 ${firstHalf.toInt()} 步，后半周（周四至周日）日均 ${secondHalf.toInt()} 步。",
                        fontSize = 13.sp,
                        color = MediumGray,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 11.sp, color = MediumGray)
    }
}

@Composable
private fun SummaryItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = BrandTeal)
        Text(label, fontSize = 13.sp, color = MediumGray)
    }
}
