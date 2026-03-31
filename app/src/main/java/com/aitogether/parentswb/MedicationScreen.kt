package com.aitogether.parentswb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitogether.parentswb.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(
    parentId: String,
    title: String,
    medication: String,
    showEcho: Boolean,
    echoText: String,
    onBack: () -> Unit
) {
    var oneTapConfirmed by remember { mutableStateOf(false) }
    var medStatus by remember { mutableStateOf<String?>(null) } // "taken" or "not_yet"
    var showMedSheet by remember { mutableStateOf(false) }
    val topBarColor = if (parentId == "mom") BrandTeal else BrandMint
    val buttonColor = if (oneTapConfirmed) BrandTeal.copy(alpha = 0.6f) else BrandTeal

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
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

            // === P0 一键确认 ===
            if (!oneTapConfirmed) {
                // 大按钮「今天我很好 ✅」
                Button(
                    onClick = {
                        oneTapConfirmed = true
                        showMedSheet = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandTealDark),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "今天我很好 ✅",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }

                Text(
                    text = "点一下就行，不用再一题一题回答了",
                    fontSize = 13.sp,
                    color = MediumGray
                )
            } else {
                // 已确认状态
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandTealLight),
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = BrandTeal,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "今天你很好 ✅",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                when (medStatus) {
                                    "taken" -> "用药已确认：吃了"
                                    "not_yet" -> "用药状态：还没吃，记得提醒哦"
                                    else -> "已记录"
                                },
                                fontSize = 14.sp,
                                color = MediumGray
                            )
                        }
                    }
                }
            }

            // 用药信息卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Medication,
                        contentDescription = null,
                        tint = topBarColor,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = medication,
                        fontSize = 16.sp,
                        color = DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

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
                            color = BrandMint
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

    // === 底部弹窗：药吃了吗？ ===
    if (showMedSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMedSheet = false },
            containerColor = White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "💊 药吃了吗？",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
                Text(
                    "就回答这一个就行",
                    fontSize = 14.sp,
                    color = MediumGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 「吃了」按钮
                Button(
                    onClick = {
                        medStatus = "taken"
                        showMedSheet = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandTeal),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("吃了 ✅", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = White)
                }

                // 「还没吃」按钮
                OutlinedButton(
                    onClick = {
                        medStatus = "not_yet"
                        showMedSheet = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WarmHighlight),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text("还没吃", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
