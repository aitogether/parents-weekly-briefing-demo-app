package com.aitogether.parentswb

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aitogether.parentswb.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParentsWBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showPrivacy by remember { mutableStateOf(!isPrivacyAcknowledged()) }

                    AppNavHost()

                    if (showPrivacy) {
                        PrivacyDialog(
                            onDismiss = {
                                acknowledgePrivacy()
                                showPrivacy = false
                            }
                        )
                    }
                }
            }
        }
    }

    private fun isPrivacyAcknowledged(): Boolean {
        return getSharedPreferences("pwb_prefs", Context.MODE_PRIVATE)
            .getBoolean("privacy_acknowledged", false)
    }

    private fun acknowledgePrivacy() {
        getSharedPreferences("pwb_prefs", Context.MODE_PRIVATE)
            .edit().putBoolean("privacy_acknowledged", true).apply()
    }
}

@Composable
fun PrivacyDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Filled.Shield,
                    contentDescription = null,
                    tint = WarmAmber,
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = "隐私说明（演示版）",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )

                HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFE5E7EB)
            )

                Text(
                    text = "本演示 App 仅用于展示「父母周报」的产品形态，所有数据均为虚构示例。\n\n本 App 不会采集、存储或上传任何真实个人信息、健康数据或位置信息。",
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = MediumGray,
                    textAlign = TextAlign.Start
                )

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmAmber),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "我知道了",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )
                }
            }
        }
    }
}
