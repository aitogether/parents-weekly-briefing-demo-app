package com.aitogether.parentswb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitogether.parentswb.ui.theme.*

data class SurveyQuestion(
    val id: Int,
    val text: String,
    val options: List<SurveyOption>
)

data class SurveyOption(
    val score: Int,
    val label: String,
    val color: Color
)

private val surveyQuestions = listOf(
    SurveyQuestion(
        id = 1,
        text = "过去 2 周，对父母健康的担心程度？",
        options = listOf(
            SurveyOption(0, "从不", WarmGreen),
            SurveyOption(1, "有时", WarmHighlight),
            SurveyOption(2, "经常", Color(0xFFE67E22)),
            SurveyOption(3, "总是", ScheduleRed)
        )
    ),
    SurveyQuestion(
        id = 2,
        text = "过去 2 周，是否因为想父母健康问题失眠？",
        options = listOf(
            SurveyOption(0, "从不", WarmGreen),
            SurveyOption(1, "有时", WarmHighlight),
            SurveyOption(2, "经常", Color(0xFFE67E22)),
            SurveyOption(3, "总是", ScheduleRed)
        )
    ),
    SurveyQuestion(
        id = 3,
        text = "过去 2 周，是否感到无法控制父母的健康状况？",
        options = listOf(
            SurveyOption(0, "从不", WarmGreen),
            SurveyOption(1, "有时", WarmHighlight),
            SurveyOption(2, "经常", Color(0xFFE67E22)),
            SurveyOption(3, "总是", ScheduleRed)
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnxietySurveyScreen(onBack: () -> Unit) {
    var answers by remember { mutableStateOf(mapOf<Int, Int>()) }
    var submitted by remember { mutableStateOf(false) }

    val totalScore = answers.values.sum()

    val feedback = when {
        totalScore <= 3 -> "一切正常，继续保持 😊"
        totalScore <= 6 -> "有些担心是正常的，建议和朋友聊聊 💬"
        else -> "担心比较多，建议找专业心理咨询 🩺"
    }

    val feedbackColor = when {
        totalScore <= 3 -> WarmGreen
        totalScore <= 6 -> WarmHighlight
        else -> ScheduleRed
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("焦虑自查", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandTeal,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
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
            // 说明卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BrandTealLight),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "📋 照顾者焦虑自查",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "以下 3 个问题帮助你了解自己近期的心理状态。答案没有对错之分，请根据真实感受选择。",
                        fontSize = 14.sp,
                        color = MediumGray,
                        lineHeight = 22.sp
                    )
                }
            }

            // 问题列表
            surveyQuestions.forEach { question ->
                QuestionCard(
                    question = question,
                    selectedScore = answers[question.id],
                    enabled = !submitted,
                    onAnswer = { score ->
                        answers = answers + (question.id to score)
                    }
                )
            }

            // 提交按钮
            if (!submitted) {
                val allAnswered = answers.size == surveyQuestions.size
                Button(
                    onClick = { submitted = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (allAnswered) BrandTeal else CardBorder
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = allAnswered
                ) {
                    Text("提交", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }

            // 结果卡片
            if (submitted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = feedbackColor.copy(alpha = 0.12f)),
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "你的得分：$totalScore / 9",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = feedbackColor
                        )
                        Text(
                            feedback,
                            fontSize = 16.sp,
                            color = DarkGray,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "这个自查仅作为参考，不构成专业诊断。如果你持续感到焦虑，建议寻求专业帮助。",
                            fontSize = 12.sp,
                            color = MediumGray,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun QuestionCard(
    question: SurveyQuestion,
    selectedScore: Int?,
    enabled: Boolean,
    onAnswer: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Q${question.id}. ${question.text}",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkGray,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                question.options.forEach { option ->
                    val isSelected = selectedScore == option.score
                    Card(
                        onClick = { if (enabled) onAnswer(option.score) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) option.color.copy(alpha = 0.2f) else option.color.copy(alpha = 0.08f)
                        ),
                        border = if (isSelected) {
                            ButtonDefaults.outlinedButtonBorder
                        } else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(24.dp)
                                    .then(
                                        Modifier.let {
                                            // color bar on left is done via background on a separate Box
                                            it
                                        }
                                    )
                            )
                            Text(
                                option.label,
                                fontSize = 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) option.color else DarkGray
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                "${option.score} 分",
                                fontSize = 13.sp,
                                color = MediumGray
                            )
                        }
                    }
                }
            }
        }
    }
}
