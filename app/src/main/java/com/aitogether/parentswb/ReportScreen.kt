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
            // 0. 一句话卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ScheduleYellow),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "妈这周有些数据不太理想，建议打个电话聊聊。",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGray,
                        lineHeight = 24.sp
                    )
                }
            }

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

            // 2. 行动按钮（并排）
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { /* TODO: 拨打电话 */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Phone, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("给妈打个电话", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
                Button(
                    onClick = { /* TODO: 发送提醒 */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmHighlight),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Medication, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("提醒妈吃药", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }

            // 3. 事实列表
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
                        Triple(Icons.Filled.DirectionsWalk, BrandMint,
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

            // 4. 行动建议
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmHighlight),
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

            // 5. 你可能好奇
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "🤔 你可能好奇",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val curiosities = listOf(
                        "为什么妈周三步数特别低？那天可能下雨了，或者身体不太舒服。",
                        "爸周六出门那 3,280 步是怎么回事？可能是去菜市场或公园了。",
                        "降压药漏服 2 天有什么影响？偶尔漏服问题不大，但连续漏服要注意。"
                    )
                    curiosities.forEach { item ->
                        Text(
                            "• $item",
                            fontSize = 14.sp,
                            color = DarkGray,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            // 6. 话题参考
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = EchoBackground),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "💬 话题参考",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val topics = listOf(
                        "\"妈，最近天气不错，有没有出去走走呀？\"",
                        "\"爸上次说膝盖疼，现在怎么样了？\"",
                        "\"我看到你周三步数特别少，是不是那天不太舒服？\"",
                        "\"降压药快吃完了没有？要不要我帮你买？\""
                    )
                    topics.forEach { topic ->
                        Text(
                            topic,
                            fontSize = 14.sp,
                            color = BrandTeal,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            // 7. 回声三选一
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = EchoBackground),
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
