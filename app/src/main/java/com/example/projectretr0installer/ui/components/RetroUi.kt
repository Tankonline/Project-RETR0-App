package com.example.projectretr0installer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectretr0installer.ui.theme.*

/* ------------------------------------------------------------------ */
/*  SECTION HEADER — small icon + UPPERCASE eyebrow + hairline rule.   */
/*  Replaces the plain floating text headers.                          */
/* ------------------------------------------------------------------ */
@Composable
fun SectionHeader(text: String, icon: ImageVector, trailingCount: Int? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
    ) {
        Icon(icon, null, tint = RetroAccent, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text.uppercase(), style = MaterialTheme.typography.labelSmall, color = RetroTextMuted)
        if (trailingCount != null) {
            Spacer(Modifier.width(8.dp))
            Text(
                trailingCount.toString(),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                color = RetroTextMuted,
                modifier = Modifier
                    .background(RetroSurface, RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
            )
        }
        Spacer(Modifier.width(10.dp))
        HorizontalDivider(color = RetroBorder, modifier = Modifier.weight(1f))
    }
}

/* ------------------------------------------------------------------ */
/*  CARD — container that groups a hub.                                */
/* ------------------------------------------------------------------ */
@Composable
fun RetroCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier
            .fillMaxWidth()
            .background(RetroSurface, RoundedCornerShape(16.dp))
            .border(1.dp, RetroBorder, RoundedCornerShape(16.dp))
            .padding(14.dp),
        content = content
    )
}

/* ------------------------------------------------------------------ */
/*  BUTTONS — one PrimaryButton per screen; everything else demoted.   */
/* ------------------------------------------------------------------ */
@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, icon: ImageVector? = null, onClick: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(RetroAccent)
            .clickable(onClick = onClick)
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(19.dp))
            Spacer(Modifier.width(9.dp))
        }
        Text(text, color = Color.White, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier = Modifier, icon: ImageVector? = null, onClick: () -> Unit) {
    Row(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(RetroSurface)
            .border(1.dp, RetroBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, null, tint = RetroTextDim, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(6.dp))
        }
        Text(
            text,
            color = RetroTextSoft,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Outlined, no fill — for low-emphasis reversible actions (e.g. "Restore original graphics"). */
@Composable
fun OutlineButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, Color(0x1FFFFFFF), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, color = RetroTextSoft, style = MaterialTheme.typography.titleMedium)
    }
}

/** Destructive / risky only (crash fix, experimental, delete). */
@Composable
fun DangerButton(text: String, modifier: Modifier = Modifier, icon: ImageVector? = null, onClick: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(RetroDangerTint)
            .border(1.dp, RetroDanger.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, null, tint = RetroDanger, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = RetroDanger, style = MaterialTheme.typography.titleMedium)
    }
}

/** Tinted accent action for "Discord" / "Subscribe" (pass tint + content color). */
@Composable
fun AccentTintButton(
    text: String, icon: ImageVector, fill: Color, stroke: Color, contentColor: Color,
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Row(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(fill)
            .border(1.dp, stroke, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = contentColor, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(6.dp))
        Text(text, color = contentColor, style = MaterialTheme.typography.bodyMedium)
    }
}

/* ------------------------------------------------------------------ */
/*  CHIP — selectable pill (graphics tiers, device presets, filters).  */
/*  name on top, optional variant subtitle below; fixed min height.    */
/* ------------------------------------------------------------------ */
@Composable
fun RetroChip(
    text: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    selected: Boolean = false,
    centered: Boolean = false,
    onClick: () -> Unit,
) {
    val bg = if (selected) RetroAccentTint else RetroSurface
    val stroke = if (selected) RetroAccent else RetroBorder
    Column(
        modifier
            .clip(RoundedCornerShape(11.dp))
            .background(bg)
            .border(if (selected) 1.5.dp else 1.dp, stroke, RoundedCornerShape(11.dp))
            .clickable(onClick = onClick)
            .defaultMinSize(minHeight = 40.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            text,
            color = if (selected) Color.White else RetroChipText,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = if (centered) TextAlign.Center else TextAlign.Start
        )
        subtitle?.let {
            Text(
                it,
                color = RetroTextMuted,
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                textAlign = if (centered) TextAlign.Center else TextAlign.Start
            )
        }
    }
}

/* ------------------------------------------------------------------ */
/*  SEGMENTED TABS — replaces underline tabs + Core/Extended/Library.  */
/* ------------------------------------------------------------------ */
@Composable
fun SegmentedTabs(
    options: List<String>, selectedIndex: Int, modifier: Modifier = Modifier, onSelect: (Int) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(RetroSurfaceAlt, RoundedCornerShape(11.dp))
            .border(1.dp, RetroBorder, RoundedCornerShape(11.dp))
            .padding(3.dp)
    ) {
        options.forEachIndexed { i, label ->
            val sel = i == selectedIndex
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (sel) RetroAccent else Color.Transparent)
                    .clickable { onSelect(i) }
                    .padding(vertical = 9.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label,
                    color = if (sel) Color.White else RetroTextDim,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun RetroIconBox(icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(36.dp)
            .background(RetroSurface, RoundedCornerShape(10.dp))
            .border(1.dp, RetroBorder, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = RetroTextDim)
    }
}

/* ------------------------------------------------------------------ */
/*  STAT CELL — labelled data cell (Level / Gold / R$ / M$).           */
/* ------------------------------------------------------------------ */
@Composable
fun RetroStatCell(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier
            .background(RetroStatBg, RoundedCornerShape(9.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp), color = RetroTextMuted)
        Text(value, fontFamily = PixelMood, color = RetroSuccess, fontSize = 14.sp) // pixel numerals read as "data"
    }
}

/* ------------------------------------------------------------------ */
/*  BOTTOM NAV — 3 items, active pill. No emoji.                       */
/* ------------------------------------------------------------------ */
data class RetroNavItem(val label: String, val icon: ImageVector, val onClick: () -> Unit)

@Composable
fun RetroBottomBar(items: List<RetroNavItem>, selectedIndex: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(RetroSurfaceAlt)
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items.forEachIndexed { i, item ->
            val sel = i == selectedIndex
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(onClick = item.onClick),
            ) {
                Box(
                    Modifier
                        .background(
                            if (sel) RetroAccentTint else Color.Transparent,
                            RoundedCornerShape(12.dp),
                        )
                        .padding(horizontal = if (sel) 16.dp else 0.dp, vertical = 6.dp),
                ) {
                    Icon(
                        item.icon,
                        null,
                        tint = if (sel) RetroAccent else RetroTextMuted,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Spacer(Modifier.height(3.dp))
                Text(
                    item.label,
                    color = if (sel) RetroAccent else RetroTextMuted,
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                )
            }
        }
    }
}

/* ------------------------------------------------------------------ */
/*  SEARCH FIELD — read-only visual shell; hoist the real query state. */
/*  For a live field, swap the Text for a BasicTextField(value=query). */
/* ------------------------------------------------------------------ */
@Composable
fun RetroSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(RetroSurface)
            .border(1.dp, RetroBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 13.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = RetroTextMuted, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(9.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = RetroText),
            modifier = Modifier.weight(1f),
            decorationBox = { inner ->
                if (value.isEmpty()) Text(placeholder, color = RetroTextMuted, style = MaterialTheme.typography.bodyMedium)
                inner()
            }
        )
    }
}
