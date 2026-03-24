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
    onAddPlanClick: () -> Unit
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
                icon = Icons.Filled.Assessment,
                color = WarmAmber,
                onClick = onReportClick
            )

            MenuButton(
                text = "💊 妈妈 · 用药确认",
                description = "看到子女的一句话",
                icon = Icons.Filled.Favorite,
                color = WarmGreen,
                onClick = onMomMedClick
            )

            MenuButton(
                text = "💚 爸爸 · 用药确认",
                description = "暂未收到回声",
                icon = Icons.Filled.MedicalServices,
                color = WarmBlue,
                onClick = onDadMedClick
            )

            MenuButton(
                text = "📝 子女端 · 添加用药计划",
                description = "演示为父母设定用药计划",
                icon = Icons.Filled.NoteAdd,
                color = Purple600,
                onClick = onAddPlanClick
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "v1.0 · ParentsWeeklyBriefing",
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
