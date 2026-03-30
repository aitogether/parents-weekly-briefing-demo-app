package com.aitogether.parentswb.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = BrandTealDark,          // v1.1: 主按钮用 BrandTealDark
    primaryContainer = BrandTealLight,
    secondary = BrandMint,
    secondaryContainer = BrandMintLight,
    tertiary = WarmHighlight,          // v1.1: 暖色点缀替代 WarmBlue
    tertiaryContainer = EchoBackground,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = DarkGray,           // v1.1: #1F2937
    onSurface = DarkGray,              // v1.1: #1F2937
    error = HeartRed,                  // v1.1: 用 HeartRed 替代 DangerRed
    outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandTealDark,
    secondary = BrandMint,
    tertiary = WarmHighlight
)

@Composable
fun ParentsWBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 始终使用品牌色，不跟随系统动态颜色
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BrandTeal.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
