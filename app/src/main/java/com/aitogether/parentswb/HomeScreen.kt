package com.aitogether.parentswb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitogether.parentswb.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onReportClick: () -> Unit,
    onMomMedClick: () -> Unit,
    onDadMedClick: () -> Unit,
    onAddPlanClick: () -> Unit,
    onStepChartClick: () -> Unit = {},
    onMultiWeekTrendClick: () -> Unit = {},
    onAnxietySurveyClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("父母周报 · 演示版", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandTeal,
                    titleContentColor = White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 一句话卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ScheduleYellow),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "妈这周步数比上周少了 15%，用药只有 5 天确认。",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGray,
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "💡 建议打个电话问问她最近是不是出门少了。",
                        fontSize = 14.sp,
                        color = MediumGray,
                        lineHeight = 22.sp
                    )
                }
            }

            Text(
                text = "请选择演示场景",
                fontSize = 18.sp,
                color = DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            MenuButton(
                text = "📋 子女端 · 本周黄灯周报",
                description = "查看本周异常摘要与回声",
                icon = Icons.Filled.Assessment,
                color = BrandTeal,
                onClick = onReportClick
            )

            MenuButton(
                text = "📊 步数趋势图",
                description = "7天步数柱状图与趋势分析",
                icon = Icons.Filled.DirectionsWalk,
                color = BrandTeal,
                onClick = onStepChartClick
            )

            MenuButton(
                text = "📈 多周趋势",
                description = "4周灯号时间线对比",
                icon = Icons.Filled.TrendingUp,
                color = BrandMint,
                onClick = onMultiWeekTrendClick
            )

            MenuButton(
                text = "📋 焦虑自查",
                description = "照顾者心理状态快速自评",
                icon = Icons.Filled.Psychology,
                color = BrandTealDark,
                onClick = onAnxietySurveyClick
            )

            MenuButton(
                text = "💊 妈妈 · 用药确认",
                description = "看到子女的一句话",
                icon = Icons.Filled.Favorite,
                color = BrandTeal,
                onClick = onMomMedClick
            )

            MenuButton(
                text = "💚 爸爸 · 用药确认",
                description = "暂未收到回声",
                icon = Icons.Filled.MedicalServices,
                color = BrandMint,
                onClick = onDadMedClick
            )

            MenuButton(
                text = "📝 子女端 · 添加用药计划",
                description = "演示为父母设定用药计划",
                icon = Icons.Filled.NoteAdd,
                color = BrandTealDark,
                onClick = onAddPlanClick
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "v2.0 · ParentsWeeklyBriefing",
                fontSize = 12.sp,
                color = MediumGray
            )
        }
    }
}

@Composable
private fun MenuButton(
    text: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = White.copy(alpha = 0.85f)
                )
            }
        }
    }
}
