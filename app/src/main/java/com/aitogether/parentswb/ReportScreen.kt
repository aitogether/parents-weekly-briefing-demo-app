package com.aitogether.parentswb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(onBack: () -> Unit) {
    var selectedEcho by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("子女端 · 本周黄灯周报", fontWeight = FontWeight.Bold) },
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
            // 1. 顶部状态卡
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BrandTealLight),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(BrandTeal)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "🟡 稍微留意一下",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "有些数据不太理想，建议打个电话聊聊。",
                            fontSize = 14.sp,
                            color = MediumGray
                        )
                    }
                }
            }

            // 2. 事实列表
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "这周发生了什么",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val facts = listOf(
                        Triple(Icons.Filled.MedicalServices, DangerRed,
                            "妈本周 7 天里有 2 天没按时吃降压药（完成率 71%）。"),
                        Triple(Icons.Filled.DirectionsWalk, BrandTeal,
                            "妈周三步数只有 890 步，比平时低很多。"),
                        Triple(Icons.Filled.DirectionsWalk, WarmBlue,
                            "爸有 5 天步数低于 800 步，周六 3,280 步出门了一次。")
                    )
                    facts.forEach { (icon, iconColor, fact) ->
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier.size(18.dp).padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(fact, fontSize = 14.sp, color = DarkGray, lineHeight = 22.sp)
                        }
                    }
                }
            }

            // 3. 行动建议
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ActionYellow),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "这周可以做点什么",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "这周抽时间打个电话给妈，问问是不是药快吃完了；顺便问问爸最近怎么都不太出门。",
                        fontSize = 14.sp,
                        color = DarkGray,
                        lineHeight = 22.sp
                    )
                }
            }

            // 4. 回声三选一
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LightGray),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "告诉爸妈你看过了（可选）",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val options = listOf(
                        "reassured" to "今天我看过你的情况，一切放心。",
                        "concerned" to "最近有点担心，改天好好跟你聊聊。",
                        "busy_caring" to "我这几天有点忙，但一直惦记着你。"
                    )
                    options.forEach { (key, text) ->
                        val isSelected = selectedEcho == key
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) BrandTeal.copy(alpha = 0.15f) else White
                            ),
                            border = if (isSelected) ButtonDefaults.outlinedButtonBorder else null,
                            onClick = { selectedEcho = key }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) BrandTeal else CardBorder)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text,
                                    fontSize = 13.sp,
                                    color = if (isSelected) BrandTeal else DarkGray,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
