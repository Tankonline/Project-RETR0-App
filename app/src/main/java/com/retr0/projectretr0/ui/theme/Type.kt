package com.retr0.projectretr0.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.retr0.projectretr0.R

val PixelMood = FontFamily(Font(R.font.hu_pixelmood_l))
val UiSans = FontFamily.SansSerif

val Typography = Typography(
    headlineMedium = TextStyle(fontFamily = PixelMood, fontSize = 22.sp, letterSpacing = 0.5.sp),
    titleLarge     = TextStyle(fontFamily = PixelMood, fontSize = 18.sp, letterSpacing = 0.5.sp),
    titleMedium = TextStyle(fontFamily = UiSans, fontWeight = FontWeight.Medium, fontSize = 15.sp),
    bodyLarge   = TextStyle(fontFamily = UiSans, fontSize = 14.sp, lineHeight = 20.sp),
    bodyMedium  = TextStyle(fontFamily = UiSans, fontSize = 13.sp, lineHeight = 18.sp),
    labelLarge  = TextStyle(fontFamily = UiSans, fontWeight = FontWeight.Medium, fontSize = 13.sp),
    labelMedium = TextStyle(fontFamily = UiSans, fontSize = 12.sp),
    labelSmall  = TextStyle(fontFamily = UiSans, fontSize = 11.sp, letterSpacing = 1.5.sp),
)
