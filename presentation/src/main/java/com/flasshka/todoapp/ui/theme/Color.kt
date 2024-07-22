package com.flasshka.todoapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

// light theme
val LightThemeSeparator = Color(0x33000000)
val LightThemeOverlay = Color(0x0F000000)

val LightThemeLabelPrimary = Color(0xFF000000)
val LightThemeLabelSecondary = Color(0x99000000)
val LightThemeLabelTertiary = Color(0x4D000000)
val LightThemeLabelDisable = Color(0x26000000)

val LightThemeRed = Color(0xFFFF3B30)
val LightThemeGreen = Color(0xFF34C759)
val LightThemeBlue = Color(0xFF007AFF)
val LightThemeGray = Color(0xFF8E8E93)
val LightThemeGrayLight = Color(0xFFD1D1D6)

val LightThemeBackPrimary = Color(0xFFF7F6F2)
val LightThemeBackSecondary = Color(0xFFFFFFFF)
val LightThemeBackElevated = Color(0xFFFFFFFF)

// dark theme
val DarkThemeSeparator = Color(0x33FFFFFF)
val DarkThemeOverlay = Color(0x52000000)

val DarkThemeLabelPrimary = Color(0xFFFFFFFF)
val DarkThemeLabelSecondary = Color(0x99FFFFFF)
val DarkThemeLabelTertiary = Color(0x66FFFFFF)
val DarkThemeLabelDisable = Color(0x26FFFFFF)

val DarkThemeRed = Color(0xFFFF453A)
val DarkThemeGreen = Color(0xFF32D74B)
val DarkThemeBlue = Color(0xFF0A84FF)
val DarkThemeGray = Color(0xFF8E8E93)
val DarkThemeGrayLight = Color(0xFF48484A)

val DarkThemeBackPrimary = Color(0xFF161618)
val DarkThemeBackSecondary = Color(0xFF252528)
val DarkThemeBackElevated = Color(0xFF3C3C3F)


var SeparatorColor = Color(0x33000000)
    private set
var OverlayColor = Color(0x0F000000)
    private set
var RedColor = Color(0xFFFF3B30)
    private set
var GreenColor = Color(0xFF34C759)
    private set
var BlueColor = Color(0xFF007AFF)
    private set
var GrayColor = Color(0xFF8E8E93)
    private set
var GrayLightColor = Color(0xFFD1D1D6)
    private set
var DisableColor = Color(0x26000000)
    private set

@Composable
fun UpdateColors(themeIsDark: Boolean) {
    if (themeIsDark) {
        SeparatorColor = Color(0x33FFFFFF)
        OverlayColor = Color(0x52000000)
        RedColor = Color(0xFFFF453A)
        GreenColor = Color(0xFF32D74B)
        BlueColor = Color(0xFF0A84FF)
        GrayColor = Color(0xFF8E8E93)
        GrayLightColor = Color(0xFF48484A)
        DisableColor = Color(0x26FFFFFF)
    } else {
        SeparatorColor = Color(0x33000000)
        OverlayColor = Color(0x0F000000)
        RedColor = Color(0xFFFF3B30)
        GreenColor = Color(0xFF34C759)
        BlueColor = Color(0xFF007AFF)
        GrayColor = Color(0xFF8E8E93)
        GrayLightColor = Color(0xFFD1D1D6)
        DisableColor = Color(0x26000000)
    }
}