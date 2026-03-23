package com.aitogether.parentswb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val WarmGreen = Color(0xFF22C55E)
private val GreenLight = Color(0xFFF0FDF4)
private val WarmAmber = Color(0xFFF59E0B)
private val White = Color.White
private val DarkGray = Color(0xFF374151)
private val MediumGray = Color(0xFF6B7280)
private val LightGray = Color(0xFFF9FAFB)
private val EchoBackground = Color(0xFFF0F9FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(
    parentId: String,
    title: String,
    medication: String,
    buttonText: String,
    showEcho: Boolean,
    echoText: String,
    onBack: () -> Unit
) {
    var confirmed by remember { mutableStateOf(false) }
    val topBarColor = if (parentId == "mom") WarmGreen else WarmBlue
    val buttonColor = if (confirmed) WarmGreen.copy(alpha = 0.6f) else WarmGreen

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor,
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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 顶部说明
            Text(
                text = medication,
                fontSize = 16.sp,
                color = DarkGray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 大按钮
            Button(
                onClick = { confirmed = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(16.dp),
                enabled = !confirmed
            ) {
                if (confirmed) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (confirmed) "✓ 已记录" else buttonText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 回声卡片（仅妈妈页显示）
            if (showEcho) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = EchoBackground),
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "💬 来自家里人的一句话",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WarmBlue
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = echoText,
                            fontSize = 16.sp,
                            color = DarkGray,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (parentId == "mom")
                    "距离上次确认 2 小时" else "暂无历史记录",
                fontSize = 12.sp,
                color = MediumGray
            )
        }
    }
}

private val WarmBlue = Color(0xFF3B82F6)
