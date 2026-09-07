/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import mozilla.components.compose.base.theme.AcornColors
import mozilla.components.compose.base.theme.AcornGradient
import mozilla.components.compose.base.theme.AcornGradientScheme
import mozilla.components.compose.base.theme.AcornGradientType
import mozilla.components.compose.base.theme.AcornTheme
import mozilla.components.compose.base.theme.AcornTypography
import mozilla.components.compose.base.utils.ColorStop
import mozilla.components.compose.base.theme.acornDarkColorScheme
import mozilla.components.compose.base.theme.acornLightColorScheme
import mozilla.components.compose.base.theme.acornPrivateColorScheme
import mozilla.components.compose.base.theme.darkAcornGradientScheme
import mozilla.components.compose.base.theme.darkColorPalette
import mozilla.components.compose.base.theme.layout.AcornLayout
import mozilla.components.compose.base.theme.layout.AcornWindowSize
import mozilla.components.compose.base.theme.lightAcornGradientScheme
import mozilla.components.compose.base.theme.lightColorPalette
import mozilla.components.compose.base.theme.privateAcornGradientScheme
import mozilla.components.compose.base.theme.privateColorPalette

import org.mozilla.fenix.ext.components

/**
 * The theme for Mozilla Firefox for Android (Fenix).
 *
 * @param theme The current [Theme] that is displayed.
 * @param content The children composables to be laid out.
 */
@Composable
fun FirefoxTheme(
    theme: Theme = getThemeProvider().provideTheme(),
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val isDynamic = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && runCatching {
        context.components.settings.themeStyle == ThemeStyle.MATERIAL_YOU
    }.getOrDefault(true)

    val colors: AcornColors = when (theme) {
        Theme.Light -> lightColorPalette
        Theme.Dark -> darkColorPalette
        Theme.Private -> privateColorPalette
    }

    val lightDynamicScheme = if (isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicLightColorScheme(context).let {
            it.copy(
                surfaceBright = colorResource(android.R.color.system_neutral1_0),
                tertiary = it.primary,
                onTertiary = it.onPrimary,
                tertiaryContainer = it.primaryContainer,
                onTertiaryContainer = it.onPrimaryContainer,
            )
        }
    } else {
        null
    }

    val darkDynamicScheme = if (isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicDarkColorScheme(context).let {
            it.copy(
                surface = it.surfaceContainer,
                tertiary = it.primary,
                onTertiary = it.onPrimary,
                tertiaryContainer = it.primaryContainer,
                onTertiaryContainer = it.onPrimaryContainer,
            )
        }
    } else {
        null
    }

    val colorScheme: ColorScheme = when (theme) {
        Theme.Private -> acornPrivateColorScheme() // ALWAYS pure stock private theme!
        Theme.Light -> lightDynamicScheme ?: acornLightColorScheme()
        Theme.Dark -> darkDynamicScheme ?: acornDarkColorScheme()
    }

    val dynamicGradients: AcornGradientScheme? = if (isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && theme != Theme.Private) {
        val isTabAreaGradient = runCatching {
            context.components.settings.isTabAreaGradientEnabled
        }.getOrDefault(true)

        val isDark = theme == Theme.Dark

        val accentGradient = if (isDark) {
            AcornGradient(
                type = AcornGradientType.Linear(angleInDegrees = 96f),
                colorStops = listOf(
                    ColorStop(0f, colorResource(android.R.color.system_accent1_300)),
                    ColorStop(0.5f, colorResource(android.R.color.system_accent2_300)),
                    ColorStop(1f, colorResource(android.R.color.system_accent3_200)),
                ),
            )
        } else {
            AcornGradient(
                type = AcornGradientType.Linear(angleInDegrees = 96f),
                colorStops = listOf(
                    ColorStop(0f, colorResource(android.R.color.system_accent1_400)),
                    ColorStop(0.5f, colorResource(android.R.color.system_accent2_400)),
                    ColorStop(1f, colorResource(android.R.color.system_accent3_300)),
                ),
            )
        }

        val tabOutlineGradient = if (isTabAreaGradient) {
            if (isDark) {
                AcornGradient(
                    type = AcornGradientType.Linear(angleInDegrees = 96f),
                    colorStops = listOf(
                        ColorStop(0f, colorResource(android.R.color.system_accent1_200)),
                        ColorStop(0.71f, colorResource(android.R.color.system_accent1_400)),
                    ),
                )
            } else {
                AcornGradient(
                    type = AcornGradientType.Linear(angleInDegrees = 96f),
                    colorStops = listOf(
                        ColorStop(0f, colorResource(android.R.color.system_accent1_500)),
                        ColorStop(0.71f, colorResource(android.R.color.system_accent1_700)),
                    ),
                )
            }
        } else {
            AcornGradient(
                type = AcornGradientType.Linear(angleInDegrees = 96f),
                colorStops = listOf(
                    ColorStop(0f, colorScheme.primary),
                    ColorStop(1f, colorScheme.primary),
                ),
            )
        }

        val accentSubtleGradient = if (isTabAreaGradient) {
            if (isDark) {
                AcornGradient(
                    type = AcornGradientType.Linear(angleInDegrees = 96f),
                    colorStops = listOf(
                        ColorStop(0f, colorResource(android.R.color.system_accent2_800).copy(alpha = 0.65f)),
                        ColorStop(1f, colorResource(android.R.color.system_accent3_800).copy(alpha = 0.65f)),
                    ),
                )
            } else {
                AcornGradient(
                    type = AcornGradientType.Linear(angleInDegrees = 96f),
                    colorStops = listOf(
                        ColorStop(0f, colorResource(android.R.color.system_accent2_100).copy(alpha = 0.65f)),
                        ColorStop(1f, colorResource(android.R.color.system_accent3_100).copy(alpha = 0.65f)),
                    ),
                )
            }
        } else {
            val flatBg = colorScheme.surfaceContainer
            AcornGradient(
                type = AcornGradientType.Linear(angleInDegrees = 96f),
                colorStops = listOf(
                    ColorStop(0f, flatBg),
                    ColorStop(1f, flatBg),
                ),
            )
        }

        val baseScheme = if (isDark) darkAcornGradientScheme else lightAcornGradientScheme
        baseScheme.copy(
            accent = accentGradient,
            accentSubtle = accentSubtleGradient,
            tabOutline = tabOutlineGradient,
        )
    } else {
        null
    }

    val gradients: AcornGradientScheme = when (theme) {
        Theme.Private -> privateAcornGradientScheme
        Theme.Light -> dynamicGradients ?: lightAcornGradientScheme
        Theme.Dark -> dynamicGradients ?: darkAcornGradientScheme
    }

    val tabGroupColors: TabGroupColorPalette = when (theme) {
        Theme.Light -> TabGroupColorPalette.lightPalette
        Theme.Dark -> TabGroupColorPalette.darkPalette
        Theme.Private -> TabGroupColorPalette.privatePalette
    }

    ProvideFirefoxTokens(tabGroupColors = tabGroupColors) {
        AcornTheme(
            colors = colors,
            colorScheme = colorScheme,
            gradients = gradients,
            content = content,
        )
    }
}

@Composable
private fun ProvideFirefoxTokens(
    tabGroupColors: TabGroupColorPalette,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        localTabGroupColors provides tabGroupColors,
        content = content,
    )
}

/**
 * Provides access to the Firefox design system tokens.
 */
object FirefoxTheme {
    val colors: AcornColors
        @Composable
        @ReadOnlyComposable
        get() = AcornTheme.colors

    val typography: AcornTypography
        get() = AcornTheme.typography

    val layout: AcornLayout
        @Composable
        @ReadOnlyComposable
        get() = AcornTheme.layout

    val windowSize: AcornWindowSize
        @Composable
        @ReadOnlyComposable
        get() = AcornTheme.windowSize

    val gradients: AcornGradientScheme
        @Composable
        @ReadOnlyComposable
        get() = AcornTheme.gradients

    val tabGroupColors: TabGroupColorPalette
        @Composable
        @ReadOnlyComposable
        get() = localTabGroupColors.current
}
