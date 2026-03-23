package com.aitogether.parentswb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onReportClick: () -> Unit,
    onMomMedClick: () -> Unit,
    onDadMedClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("父母周报 · 演示版", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WarmAmber,
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

            Text(
                text = "请选择演示场景",
                fontSize = 18.sp,
                color = DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            MenuButton(
                text = "📋 子女端 · 本周黄灯周报",
                description = "查看本周异常摘要与回声",
                color = WarmAmber,
                onClick = onReportClick
            )

            MenuButton(
                text = "💊 妈妈 · 用药确认",
                description = "看到子女的一句话",
                color = WarmGreen,
                onClick = onMomMedClick
            )

            MenuButton(
                text = "💚 爸爸 · 用药确认",
                description = "暂未收到回声",
                color = WarmBlue,
                onClick = onDadMedClick
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "v1.0 · ParentsWeeklyBriefing",
                fontSize = 12.sp,
                color = LightGray
            )
        }
    }
}

@Composable
private fun MenuButton(
    text: String,
    description: String,
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
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    fontSize = 18.sp,
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

private val WarmAmber = androidx.compose.ui.graphics.Color(0xFFF59E0B)
private val WarmGreen = androidx.compose.ui.graphics.Color(0xFF22C55E)
private val WarmBlue = androidx.compose.ui.graphics.Color(0xFF3B82F6)
private val White = androidx.compose.ui.graphics.Color.White
private val DarkGray = androidx.compose.ui.graphics.Color(0xFF4B5563)
private val LightGray = androidx.compose.ui.graphics.Color(0xFF9CA3AF)
