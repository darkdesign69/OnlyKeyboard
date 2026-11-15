package dev.darkdesign69.onlykeyboard.feature.ime.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier

/** Click with an extra long-press action. Kept in its own file so the large keyboard view stays small. */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.tapOrLongClick(onTap: () -> Unit, onLongPress: () -> Unit): Modifier =
    this.combinedClickable(onClick = onTap, onLongClick = onLongPress)
