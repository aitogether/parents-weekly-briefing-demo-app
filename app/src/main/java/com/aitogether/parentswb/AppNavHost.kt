package com.aitogether.parentswb

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Report : Screen("report")
    object MedicationMom : Screen("medication/mom")
    object MedicationDad : Screen("medication/dad")
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onReportClick = { navController.navigate(Screen.Report.route) },
                onMomMedClick = { navController.navigate(Screen.MedicationMom.route) },
                onDadMedClick = { navController.navigate(Screen.MedicationDad.route) }
            )
        }
        composable(Screen.Report.route) {
            ReportScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.MedicationMom.route) {
            MedicationScreen(
                parentId = "mom",
                title = "妈妈 · 用药确认",
                medication = "今天的用药：降压药早晚各一片",
                buttonText = "今天已吃药",
                showEcho = true,
                echoText = "最近有点担心，改天好好跟你聊聊。",
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.MedicationDad.route) {
            MedicationScreen(
                parentId = "dad",
                title = "爸爸 · 用药确认",
                medication = "今天的用药：今天没有特别需要记的药。",
                buttonText = "状态正常",
                showEcho = false,
                echoText = "",
                onBack = { navController.popBackStack() }
            )
        }
    }
}
