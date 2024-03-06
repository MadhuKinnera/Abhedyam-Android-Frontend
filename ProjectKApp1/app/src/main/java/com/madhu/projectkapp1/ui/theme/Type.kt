package com.madhu.projectkapp1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.madhu.projectkapp1.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val acmeFontName = GoogleFont("Acme")

val acmeFontFamily = FontFamily(
    Font(
        googleFont = acmeFontName,
        fontProvider = provider
    )
)


val kdamFontName = GoogleFont("Josefin Sans")

val kdamFontFamily = FontFamily(
    Font(
        googleFont = kdamFontName,
        fontProvider = provider
    )
)


val vinaSansFont = GoogleFont("Lilita One")

val vinaSansFontFamily = FontFamily(
    Font(
        googleFont = vinaSansFont,
        fontProvider = provider
    )
)

val caveatFont = GoogleFont("Caveat")

val caveatFontFamily = FontFamily(
    Font(
        googleFont = caveatFont,
        fontProvider = provider
    )
)



val josephFont = GoogleFont("Josefin Sans")

val josephFontFamily = FontFamily(
    Font(
        googleFont = josephFont,
        fontProvider = provider
    )
)

val francoisFont = GoogleFont("Francois One")

val francoisFontFamily = FontFamily(
    Font(
        googleFont = francoisFont,
        fontProvider = provider
    )
)

val bungeeFont = GoogleFont("Bungee")

val bungeeFontFamily = FontFamily(
    Font(
        googleFont = francoisFont,
        fontProvider = provider
    )
)







val overPassFont = GoogleFont("Overpass")

val overPassFontFamily = FontFamily(
    Font(
        googleFont = overPassFont,
        fontProvider = provider
    )
)



val vollKornPassFont = GoogleFont("Vollkorn")

val vollKornFontFamily = FontFamily(
    Font(
        googleFont = vollKornPassFont,
        fontProvider = provider
    )
)



val philosopherFont = GoogleFont("Philosopher")

val philosopherFontFamily = FontFamily(
    Font(
        googleFont = philosopherFont,
        fontProvider = provider
    )
)


val EczarFontName = GoogleFont("Eczar")

val EczarFontFamily = FontFamily(
    Font(
        googleFont = EczarFontName,
        fontProvider = provider
    )
)

val BubblegumSansFontName = GoogleFont("Bubblegum Sans")

val BubblegumSansFontFamily = FontFamily(
    Font(
        googleFont = BubblegumSansFontName,
        fontProvider = provider
    )
)


val StaatlichesFontName = GoogleFont("Staatliches")

val StaatlichesFontFamily = FontFamily(
    Font(
        googleFont = StaatlichesFontName,
        fontProvider = provider
    )
)


val OxygenMonoFontName = GoogleFont("Oxygen Mono")

val OxygenMonoFontFamily = FontFamily(
    Font(
        googleFont = OxygenMonoFontName,
        fontProvider = provider
    )
)





val FiraMonoFontName = GoogleFont("Fira Mono")

val FiraMonoFontFamily = FontFamily(
    Font(
        googleFont = FiraMonoFontName,
        fontProvider = provider
    )
)
val SilkScreenFontName = GoogleFont("Silkscreen")

val SilkScreenFontFamily = FontFamily(
    Font(
        googleFont = SilkScreenFontName,
        fontProvider = provider
    )
)




// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge =  TextStyle(
        fontFamily = acmeFontFamily,
        fontSize = 24.sp,
        color = Color.Blue
    ), titleMedium = TextStyle(
        fontFamily = EczarFontFamily,
        fontWeight = FontWeight.Normal,
    ), bodyLarge = TextStyle(
        fontFamily = FiraMonoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ), bodyMedium = TextStyle(
        fontFamily = EczarFontFamily,
        fontWeight = FontWeight.Normal,
    ),bodySmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 16.sp,
    ),

)