package com.aitogether.parentswb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitogether.parentswb.ui.theme.*
import kotlin.random.Random

enum class TrafficLight { GREEN, YELLOW, RED }

data class WeekStatus(
    val weekLabel: String,
    val stepLight: TrafficLight,
    val medLight: TrafficLight,
    val dailyDots: List<Color>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiWeekTrendScreen(onBack: () -> Unit) {
    val mockWeeks = remember {
        val weekLabels = listOf("第1周", "第2周", "第3周", "本周")
        weekLabels.map { label ->
            val step = TrafficLight.entries.toTypedArray()[Random.nextInt(3)]
            val med = TrafficLight.entries.toTypedArray()[Random.nextInt(3)]
            val baseColor = when (step) {
                TrafficLight.GREEN -> WarmGreen
                TrafficLight.YELLOW -> WarmHighlight
                TrafficLight.RED -> ScheduleRed
            }
            val dots = (0..6).map { i ->
                if (i % 2 == 0) baseColor else baseColor.copy(alpha = 0.7f)
            }
            WeekStatus(
                weekLabel = label,
                stepLight = step,
                medLight = med,
                dailyDots = dots
            )
        }
    }

    fun lightColor(l: TrafficLight) = when (l) {
        TrafficLight.GREEN -> WarmGreen
        TrafficLight.YELLOW -> WarmHighlight
        TrafficLight.RED -> ScheduleRed
    }

    fun lightEmoji(l: TrafficLight) = when (l) {
        TrafficLight.GREEN -> "🟢"
        TrafficLight.YELLOW -> "🟡"
        TrafficLight.RED -> "🔴"
    }

    fun trafficLightToScore(l: TrafficLight) = when (l) {
        TrafficLight.GREEN -> 3
        TrafficLight.YELLOW -> 2
        TrafficLight.RED -> 1
    }

    // 步数趋势判断
    val stepScores = mockWeeks.map { trafficLightToScore(it.stepLight) }
    val stepTrend = when {
        stepScores.last() > stepScores.first() -> "📈 步数趋势：改善中"
        stepScores.last() < stepScores.first() -> "📉 步数趋势：有所下降"
        else -> "➡️ 步数趋势：保持稳定"
    }

    // 用药趋势判断
    val medScores = mockWeeks.map { trafficLightToScore(it.medLight) }
    val medTrend = when {
        medScores.last() > medScores.first() -> "📈 用药趋势：改善中"
        medScores.last() < medScores.first() -> "📉 用药趋势：有所下降"
        else -> "➡️ 用药趋势：保持稳定"
    }

    // 统计
    val stepGreenCount = mockWeeks.count { it.stepLight == TrafficLight.GREEN }
    val stepYellowCount = mockWeeks.count { it.stepLight == TrafficLight.YELLOW }
    val stepRedCount = mockWeeks.count { it.stepLight == TrafficLight.RED }
    val medGreenCount = mockWeeks.count { it.medLight == TrafficLight.GREEN }
    val medYellowCount = mockWeeks.count { it.medLight == TrafficLight.YELLOW }
    val medRedCount = mockWeeks.count { it.medLight == TrafficLight.RED }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("妈 · 多周趋势", fontWeight = FontWeight.Bold) },
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
            // 步数灯号时间线
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("👣 步数灯号时间线", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Spacer(modifier = Modifier.height(12.dp))

                    mockWeeks.forEach { week ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                week.weekLabel,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkGray,
                                modifier = Modifier.width(60.dp)
                            )
                            // 灯号圆点（连续7天）
                            week.dailyDots.forEach { dotColor ->
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(dotColor)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                lightEmoji(week.stepLight),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // 用药灯号时间线
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("💊 用药灯号时间线", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Spacer(modifier = Modifier.height(12.dp))

                    mockWeeks.forEach { week ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                week.weekLabel,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkGray,
                                modifier = Modifier.width(60.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(lightColor(week.medLight))
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                when (week.medLight) {
                                    TrafficLight.GREEN -> "全部按时"
                                    TrafficLight.YELLOW -> "偶有遗漏"
                                    TrafficLight.RED -> "多次漏服"
                                },
                                fontSize = 13.sp,
                                color = MediumGray
                            )
                        }
                    }
                }
            }

            // 趋势总结
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BrandTealLight),
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("📈 趋势总结", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Text(stepTrend, fontSize = 14.sp, color = DarkGray, lineHeight = 22.sp)
                    Text(medTrend, fontSize = 14.sp, color = DarkGray, lineHeight = 22.sp)
                }
            }

            // 灯号统计
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("📊 灯号统计（近4周）", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("步数", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatChip(WarmGreen, "🟢 绿灯 $stepGreenCount 周")
                        StatChip(WarmHighlight, "🟡 黄灯 $stepYellowCount 周")
                        StatChip(ScheduleRed, "🔴 红灯 $stepRedCount 周")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("用药", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatChip(WarmGreen, "🟢 绿灯 $medGreenCount 周")
                        StatChip(WarmHighlight, "🟡 黄灯 $medYellowCount 周")
                        StatChip(ScheduleRed, "🔴 红灯 $medRedCount 周")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatChip(dotColor: Color, text: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = dotColor.copy(alpha = 0.12f)
    ) {
        Text(
            text,
            fontSize = 12.sp,
            color = DarkGray,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
