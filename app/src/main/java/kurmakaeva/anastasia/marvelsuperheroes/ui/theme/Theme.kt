package kurmakaeva.anastasia.marvelsuperheroes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = RedPrimary,
    primaryVariant = RedPrimaryVariant,
    secondary = Secondary,
    background = Grey
)

private val LightColorPalette = lightColors(
    primary = RedPrimary,
    primaryVariant = RedPrimaryVariant,
    secondary = Secondary,
    background = Grey,
    surface = White
)

@Composable
fun MarvelSuperHeroesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}