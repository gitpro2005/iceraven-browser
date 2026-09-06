/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.theme

import android.os.Build

/**
 * Defines the available theme palettes/styles for the browser.
 */
enum class ThemeStyle(val value: String) {
    CLASSIC("classic"),
    MATERIAL_YOU("material_you"),
    AMOLED("amoled");

    companion object {
        fun fromValue(value: String): ThemeStyle =
            entries.find { it.value == value } ?: defaultThemeStyle()

        /**
         * The default theme style out of the box.
         * Defaults to MATERIAL_YOU on Android 12+ (API 31+), and CLASSIC on older versions.
         */
        fun defaultThemeStyle(): ThemeStyle =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MATERIAL_YOU else CLASSIC
    }
}
