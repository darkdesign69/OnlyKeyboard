package dev.darkdesign69.onlykeyboard.kiebord.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.darkdesign69.onlykeyboard.data.prefs.KiebordPreferences
import dev.darkdesign69.onlykeyboard.data.prefs.KiebordPreferencesRepository
import kotlin.math.roundToInt

/** Runs a preferences write on the activity scope. */
typealias PrefsUpdater = (suspend KiebordPreferencesRepository.() -> Unit) -> Unit

private fun pos(index: Int, count: Int): GroupPosition = when {
    count <= 1 -> GroupPosition.ALONE
    index == 0 -> GroupPosition.TOP
    index == count - 1 -> GroupPosition.BOTTOM
    else -> GroupPosition.MIDDLE
}

@Composable
private fun PageColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        content()
    }
}

@Composable
private fun Toggle(
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    index: Int,
    count: Int,
    onChange: (Boolean) -> Unit
) {
    SettingsToggleItem(
        title = tr(title),
        subtitle = subtitle?.let { tr(it) },
        checked = checked,
        position = pos(index, count),
        onCheckedChange = onChange
    )
}

// ---------------------------------------------------------------------------
// Extra items appended to the Preferences page (items the old page did not have)
// ---------------------------------------------------------------------------
@Composable
fun PreferencesExtras(preferences: KiebordPreferences, update: PrefsUpdater) {
    Spacer(modifier = Modifier.height(8.dp))
    SettingsCategoryHeader(title = tr("Typing & Clipboard"))
    val n = 5
    SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
        Toggle("Auto-capitalization", "Capitalize the first word of each sentence", preferences.autoCapitalization, 0, n) { v -> update { updateAutoCapitalization(v) } }
        Toggle("Double-space period", "Double tap on spacebar inserts a period followed by a space", preferences.doubleSpacePeriod, 1, n) { v -> update { updateDoubleSpacePeriod(v) } }
        Toggle("Double-space tab", "Double tap on spacebar inserts a tab", preferences.doubleSpaceTab, 2, n) { v -> update { updateDoubleSpaceTab(v) } }
        Toggle("Voice input key", null, preferences.voiceTypingEnabled, 3, n) { v -> update { updateVoiceTypingEnabled(v) } }
        Toggle("Cut Copy Paste Feature", "Long press on X, C, V for cut, copy, paste", preferences.cutCopyPasteLongPress, 4, n) { v -> update { updateCutCopyPasteLongPress(v) } }
    }

    Spacer(modifier = Modifier.height(16.dp))
    val clipCount = if (preferences.autoDeleteRecentClips) 4 else 3
    SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
        Toggle("Clipboard Recent Items", "Show recent copied or cut text in clipboard", preferences.clipboardRecentItems, 0, clipCount) { v -> update { updateClipboardRecentItems(v) } }
        Toggle("Keep copied text on Clipboard", "Time before automatically delete unpinned items", !preferences.autoDeleteRecentClips, 1, clipCount) { keep ->
            update { updateAutoDeleteRecentClips(!keep) }
        }
        if (preferences.autoDeleteRecentClips) {
            val options = listOf(1, 6, 12, 24, 72, 168)
            val idx = options.indexOfFirst { it >= preferences.autoDeleteRecentClipsDurationHours }.let { if (it < 0) options.lastIndex else it }
            SettingsSliderItem(
                title = tr("Time before automatically delete unpinned items"),
                value = idx.toFloat(),
                valueRange = 0f..(options.size - 1).toFloat(),
                steps = options.size - 2,
                valueLabel = hoursLabel(options[idx]),
                position = pos(2, clipCount),
                onValueChange = { v -> update { updateAutoDeleteRecentClipsDurationHours(options[v.roundToInt().coerceIn(0, options.size - 1)]) } }
            )
        }
        Toggle("Show copied images on Clipboard", "Show screenshots or copied images on Clipboard", preferences.clipboardShowImages, clipCount - 1, clipCount) { v -> update { updateClipboardShowImages(v) } }
    }

    Spacer(modifier = Modifier.height(16.dp))
    val keysCount = if (preferences.showGlobeKey) 2 else 1
    SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (preferences.showGlobeKey) {
            Toggle("Allow Other Keyboards", "Globe key switches to other keyboards also", preferences.globeOpensOtherKeyboards, 0, keysCount) { v -> update { updateGlobeOpensOtherKeyboards(v) } }
        }
        Toggle("Smart volume key cursor control", "Disable cursor movement using volume keys during audio playback", preferences.smartVolumeCursor, keysCount - 1, keysCount) { v -> update { updateSmartVolumeCursor(v) } }
    }
}

private fun hoursLabel(hours: Int): String = when {
    hours < 24 -> "$hours h"
    else -> "${hours / 24} d"
}

// ---------------------------------------------------------------------------
// Appearance & Layouts
// ---------------------------------------------------------------------------
@Composable
fun AppearanceLayoutsScreen(preferences: KiebordPreferences, update: PrefsUpdater) {
    PageColumn {
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            Toggle("Enable number row", "Adds an extra row", preferences.showNumberRow, 0, 4) { v -> update { updateShowNumberRow(v) } }
            Toggle("Large number row", null, preferences.largeNumberRow, 1, 4) { v -> update { updateLargeNumberRow(v) } }
            Toggle("Hide long press hints", "Hide small labels from key corners for a cleaner view", !preferences.showSecondaryKeys, 2, 4) { hide -> update { updateShowSecondaryKeys(!hide) } }
            Toggle("Enable keyboard resizing", null, preferences.keyboardResizingEnabled, 3, 4) { v -> update { updateKeyboardResizingEnabled(v) } }
        }

        if (preferences.keyboardResizingEnabled) {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
                SettingsSliderItem(
                    title = tr("Keyboard Height (Portrait)"),
                    value = preferences.keyboardHeightScale * 100f,
                    valueRange = 60f..140f,
                    steps = 15,
                    valueLabel = "${(preferences.keyboardHeightScale * 100f).roundToInt()}%",
                    position = GroupPosition.TOP,
                    onValueChange = { v -> update { updateKeyboardHeightScale((v / 5f).roundToInt() * 5 / 100f) } }
                )
                SettingsSliderItem(
                    title = tr("Keyboard Height (Landscape)"),
                    value = preferences.keyboardHeightScaleLandscape * 100f,
                    valueRange = 60f..140f,
                    steps = 15,
                    valueLabel = "${(preferences.keyboardHeightScaleLandscape * 100f).roundToInt()}%",
                    position = GroupPosition.BOTTOM,
                    onValueChange = { v -> update { updateKeyboardHeightScaleLandscape((v / 5f).roundToInt() * 5 / 100f) } }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            SettingsSliderItem(
                title = tr("One Handed Keyboard Width (Portrait)"),
                value = preferences.oneHandedWidthPortraitPercent.toFloat(),
                valueRange = 50f..95f,
                steps = 8,
                valueLabel = "${preferences.oneHandedWidthPortraitPercent}%",
                position = GroupPosition.TOP,
                onValueChange = { v -> update { updateOneHandedWidthPortraitPercent((v / 5f).roundToInt() * 5) } }
            )
            SettingsSliderItem(
                title = tr("One Handed Keyboard Width (Landscape)"),
                value = preferences.oneHandedWidthLandscapePercent.toFloat(),
                valueRange = 30f..90f,
                steps = 11,
                valueLabel = "${preferences.oneHandedWidthLandscapePercent}%",
                position = GroupPosition.BOTTOM,
                onValueChange = { v -> update { updateOneHandedWidthLandscapePercent((v / 5f).roundToInt() * 5) } }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            Toggle("Enable split keyboard", "Splits the keys in two halves in landscape", preferences.splitKeyboardEnabled, 0, 3) { v -> update { updateSplitKeyboardEnabled(v) } }
            Toggle("Enable split keyboard for foldable phone", "Splits the keys when the screen is wide (unfolded)", preferences.splitKeyboardFoldable, 1, 3) { v -> update { updateSplitKeyboardFoldable(v) } }
            Toggle("Forced enter button", "Do not show emoji on enter button", preferences.forcedEnterButton, 2, 3) { v -> update { updateForcedEnterButton(v) } }
        }
    }
}

// ---------------------------------------------------------------------------
// Extra items appended to the Text correction page
// ---------------------------------------------------------------------------
@Composable
fun CorrectionExtras(preferences: KiebordPreferences, update: PrefsUpdater) {
    Spacer(modifier = Modifier.height(8.dp))
    SettingsCategoryHeader(title = tr("Spelling"))
    SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
        Toggle("Block offensive words", "Do not suggest potentially offensive words", preferences.blockOffensiveWords, 0, 2) { v -> update { updateBlockOffensiveWords(v) } }
        Toggle("Auto-correction", "Spacebar and punctuation automatically correct mistyped words", preferences.autoCorrection, 1, 2) { v -> update { updateAutoCorrection(v) } }
    }
}

// ---------------------------------------------------------------------------
// Advanced
// ---------------------------------------------------------------------------
@Composable
fun AdvancedSettingsScreen(
    preferences: KiebordPreferences,
    update: PrefsUpdater
) {
    PageColumn {
        val vibrate = preferences.vibrateOnKeypress
        val sliderCount = 3 + (if (vibrate) 1 else 0)
        var i = 0
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            SettingsSliderItem(
                title = tr("Key popup dismiss delay"),
                value = preferences.keyPopupDismissDelayMs.toFloat(),
                valueRange = 0f..500f,
                steps = 9,
                valueLabel = if (preferences.keyPopupDismissDelayMs == 0) tr("Default") else "${preferences.keyPopupDismissDelayMs}ms",
                position = pos(i++, sliderCount),
                onValueChange = { v -> update { updateKeyPopupDismissDelayMs((v / 50f).roundToInt() * 50) } }
            )
            if (vibrate) {
                SettingsSliderItem(
                    title = tr("Keypress vibration duration"),
                    value = preferences.vibrationDurationMs.toFloat(),
                    valueRange = 0f..60f,
                    steps = 11,
                    valueLabel = if (preferences.vibrationDurationMs == 0) tr("System default") else "${preferences.vibrationDurationMs}ms",
                    position = pos(i++, sliderCount),
                    onValueChange = { v -> update { updateVibrationDurationMs((v / 5f).roundToInt() * 5) } }
                )
            }
            SettingsSliderItem(
                title = tr("Space cursor long press delay"),
                value = preferences.spaceCursorLongPressDelayMs.toFloat(),
                valueRange = 150f..1500f,
                steps = 26,
                valueLabel = "${preferences.spaceCursorLongPressDelayMs}ms",
                position = pos(i++, sliderCount),
                onValueChange = { v -> update { updateSpaceCursorLongPressDelayMs((v / 50f).roundToInt() * 50) } }
            )
            SettingsSliderItem(
                title = tr("Space cursor speed"),
                value = preferences.spaceCursorSpeed.toFloat(),
                valueRange = 50f..300f,
                steps = 24,
                valueLabel = "${preferences.spaceCursorSpeed}",
                position = pos(i++, sliderCount),
                onValueChange = { v -> update { updateSpaceCursorSpeed((v / 10f).roundToInt() * 10) } }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            Toggle("Emoji for physical keyboard", "Physical Alt key shows the emoji palette", preferences.physicalEmojiKey, 0, 1) { v -> update { updatePhysicalEmojiKey(v) } }
        }
    }
}

// ---------------------------------------------------------------------------
// Gif quality
// ---------------------------------------------------------------------------
@Composable
fun GifQualityScreen(preferences: KiebordPreferences, update: PrefsUpdater) {
    PageColumn {
        val options = listOf(
            Triple(KiebordPreferences.GIF_QUALITY_LOW, "Low", "Smaller files, faster sending"),
            Triple(KiebordPreferences.GIF_QUALITY_MEDIUM, "Medium", "Balanced size and quality"),
            Triple(KiebordPreferences.GIF_QUALITY_HIGH, "High", "Best quality, larger files")
        )
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            options.forEachIndexed { index, (id, title, subtitle) ->
                SettingsRadioOption(
                    title = tr(title),
                    subtitle = tr(subtitle),
                    selected = preferences.gifQuality == id,
                    position = pos(index, options.size),
                    onSelect = { update { updateGifQuality(id) } }
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// App language
// ---------------------------------------------------------------------------
@Composable
fun AppLanguageScreen(preferences: KiebordPreferences, update: PrefsUpdater) {
    PageColumn {
        SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
            SettingsRadioOption(
                title = tr("English"),
                selected = preferences.appLanguage == KiebordPreferences.APP_LANGUAGE_ENGLISH,
                position = GroupPosition.TOP,
                onSelect = { update { updateAppLanguage(KiebordPreferences.APP_LANGUAGE_ENGLISH) } }
            )
            SettingsRadioOption(
                title = "বাংলা",
                selected = preferences.appLanguage == KiebordPreferences.APP_LANGUAGE_BANGLA,
                position = GroupPosition.BOTTOM,
                onSelect = { update { updateAppLanguage(KiebordPreferences.APP_LANGUAGE_BANGLA) } }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Release notes
// ---------------------------------------------------------------------------
@Composable
fun ReleaseNotesScreen() {
    val colors = LocalSettingsColors.current
    val notes = listOf(
        "0.3.0" to listOf(
            "New Quick Settings pages: Typing Preferences, Appearance & Layouts, Text correction, Advanced",
            "Large number row, keyboard resizing switch and one-handed width",
            "Split keyboard (landscape and foldable)",
            "Auto-correction and offensive word blocking",
            "Clipboard recent items switch and copied images",
            "Key popup delay, vibration duration, space cursor delay and speed",
            "Physical Alt key opens the emoji palette",
            "Bangla / English app language"
        ),
        "0.2.9" to listOf("Previous release")
    )
    PageColumn {
        notes.forEach { (version, items) ->
            SettingsCategoryHeader(title = "v$version")
            SettingsGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
                SettingsCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        items.forEach { line ->
                            Text(
                                text = "•  $line",
                                color = colors.textPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
