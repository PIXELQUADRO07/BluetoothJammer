package com.eikarna.bluetoothjammer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eikarna.bluetoothjammer.ui.theme.CyberPrimary
import com.eikarna.bluetoothjammer.ui.theme.CyberSecondary

@Composable
fun CyberButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = CyberPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .border(
                BorderStroke(2.dp, color),
                shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = color
        ),
        shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}

@Composable
fun CyberCard(
    modifier: Modifier = Modifier,
    borderColor: Color = CyberSecondary,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, borderColor),
                shape = CutCornerShape(topEnd = 16.dp, bottomStart = 16.dp)
            )
            .background(
                Color.Black.copy(alpha = 0.5f),
                shape = CutCornerShape(topEnd = 16.dp, bottomStart = 16.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun GlitchText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = CyberPrimary
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            color = CyberPrimary.copy(alpha = 0.3f),
            modifier = Modifier.offset(x = (-2).dp, y = 1.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = text,
            color = CyberPrimary.copy(alpha = 0.3f),
            modifier = Modifier.offset(x = 2.dp, y = (-1).dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
