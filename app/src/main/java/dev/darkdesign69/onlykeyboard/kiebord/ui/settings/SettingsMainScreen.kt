package dev.darkdesign69.onlykeyboard.kiebord.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.NewReleases
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SettingsBackupRestore
import androidx.compose.material.icons.outlined.Spellcheck
import androidx.compose.material.icons.outlined.Gesture
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.darkdesign69.onlykeyboard.kiebord.R
import dev.darkdesign69.onlykeyboard.data.prefs.KiebordPreferences

@Composable
fun SettingsMainScreen(
    preferences: KiebordPreferences,
    isImeEnabled: Boolean,
    isImeSelected: Boolean,
    onOpenSettings: () -> Unit,
    onSwitchIme: () -> Unit,
    onNavigate: (SettingsScreen) -> Unit
) {
    val scrollState = rememberScrollState()
    val colors = LocalSettingsColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // Activation / Status Banner (inspired by SetupStatusPreference)
        if (!isImeEnabled || !isImeSelected) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colors.warningCardBg),
                border = BorderStroke(1.dp, colors.warningCardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (!isImeEnabled) "Keyboard Disabled" else "Keyboard Not Active",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.warningCardText
                    )
                    Text(
                        text = if (!isImeEnabled) {
                            "Enable Only Keyboard in Android system settings to begin typing."
                        } else {
                            "Only Keyboard is enabled, but not set as your active input method."
                        },
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = colors.warningCardSubtext,
                        modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = if (!isImeEnabled) onOpenSettings else onSwitchIme,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.warningBtnBg,
                                contentColor = colors.warningBtnText
                            )
                        ) {
                            Icon(
                                imageVector = if (!isImeEnabled) Icons.Filled.Settings else Icons.Outlined.SwapHoriz,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (!isImeEnabled) "Turn On" else "Switch Back",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Button(
                            onClick = { onNavigate(SettingsScreen.Onboarding) },
                            modifier = Modifier.height(44.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.warningSecondaryBtnBg,
                                contentColor = colors.warningSecondaryBtnText
                            ),
                            border = BorderStroke(1.dp, colors.warningSecondaryBtnBorder)
                        ) {
                            Text(
                                text = "Setup Wizard",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colors.activeCardBg),
                border = BorderStroke(1.dp, colors.activeCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher),
                        contentDescription = "Only Keyboard Logo",
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Only Keyboard is Active",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.activeCardText
                        )
                        Text(
                            text = "Ready to type in all applications",
                            fontSize = 12.sp,
                            color = colors.activeCardSubtext
                        )
                    }
                    Button(
                        onClick = { onNavigate(SettingsScreen.Onboarding) },
                        shape = CircleShape,
                        modifier = Modifier.height(36.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.activeBtnBg,
                            contentColor = colors.activeBtnText
                        ),
                        border = BorderStroke(1.dp, colors.activeBtnBorder)
                    ) {
                        Text(
                            text = "Wizard",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsGroup {
            SettingsNavigationItem(
                title = tr("Appearance & Themes"),
                icon = Icons.Outlined.Palette,
                position = GroupPosition.TOP,
                onClick = { onNavigate(SettingsScreen.AppearanceThemes) }
            )

            SettingsNavigationItem(
                title = tr("Keyboard Layouts"),
                icon = Icons.Outlined.Keyboard,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.KeyboardLayouts) }
            )

            SettingsNavigationItem(
                title = tr("Language Extensions"),
                icon = Icons.Outlined.Language,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.LanguageExtensions) }
            )

            SettingsNavigationItem(
                title = tr("Preferences"),
                icon = Icons.Outlined.Tune,
                position = GroupPosition.BOTTOM,
                onClick = { onNavigate(SettingsScreen.Preferences) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsGroup {
            SettingsNavigationItem(
                title = tr("Corrections & Suggestions"),
                icon = Icons.Outlined.Spellcheck,
                position = GroupPosition.TOP,
                onClick = { onNavigate(SettingsScreen.CorrectionsSuggestions) }
            )

            SettingsNavigationItem(
                title = tr("Smart Gestures"),
                icon = Icons.Outlined.Gesture,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.SmartGestures) }
            )

            SettingsNavigationItem(
                title = tr("Voice Typing"),
                icon = Icons.Outlined.Mic,
                position = GroupPosition.BOTTOM,
                onClick = { onNavigate(SettingsScreen.VoiceTyping) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsGroup {
            SettingsNavigationItem(
                title = tr("Clipboard"),
                icon = Icons.Outlined.ContentPaste,
                position = GroupPosition.TOP,
                onClick = { onNavigate(SettingsScreen.Clipboard) }
            )

            SettingsNavigationItem(
                title = tr("Personal Dictionary"),
                icon = Icons.AutoMirrored.Outlined.MenuBook,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.PersonalDictionary) }
            )

            SettingsNavigationItem(
                title = tr("Backup & Restore"),
                icon = Icons.Outlined.SettingsBackupRestore,
                position = GroupPosition.BOTTOM,
                onClick = { onNavigate(SettingsScreen.BackupRestore) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsCategoryHeader(title = tr("Quick Settings"))
        SettingsGroup {
            SettingsNavigationItem(
                title = tr("Preferences"),
                icon = Icons.Outlined.Tune,
                position = GroupPosition.TOP,
                onClick = { onNavigate(SettingsScreen.TypingPreferences) }
            )
            SettingsNavigationItem(
                title = tr("Appearance & Layouts"),
                icon = Icons.Outlined.Keyboard,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.AppearanceLayouts) }
            )
            SettingsNavigationItem(
                title = tr("Text correction"),
                icon = Icons.Outlined.Spellcheck,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.TextCorrection) }
            )
            SettingsNavigationItem(
                title = tr("More Languages"),
                subtitle = tr("Install Arabic, Chakma, Syloti language"),
                icon = Icons.Outlined.Language,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.LanguageExtensions) }
            )
            SettingsNavigationItem(
                title = tr("Advanced"),
                icon = Icons.Outlined.Settings,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.AdvancedSettings) }
            )
            SettingsNavigationItem(
                title = tr("Gif Quality"),
                icon = Icons.Outlined.Image,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.GifQuality) }
            )
            SettingsNavigationItem(
                title = tr("Release notes"),
                icon = Icons.Outlined.NewReleases,
                position = GroupPosition.MIDDLE,
                onClick = { onNavigate(SettingsScreen.ReleaseNotes) }
            )
            SettingsNavigationItem(
                title = tr("App Language"),
                subtitle = tr("Change between Bangla and English"),
                icon = Icons.Outlined.Translate,
                position = GroupPosition.BOTTOM,
                onClick = { onNavigate(SettingsScreen.AppLanguage) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsGroup {
            SettingsNavigationItem(
                title = tr("About Only Keyboard"),
                iconResId = R.drawable.ic_launcher,
                position = GroupPosition.ALONE,
                onClick = { onNavigate(SettingsScreen.About) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
