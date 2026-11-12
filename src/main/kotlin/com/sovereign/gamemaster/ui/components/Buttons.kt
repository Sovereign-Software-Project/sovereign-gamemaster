package com.sovereign.gamemaster.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

enum class ButtonIntent {
    NEUTRAL,
    SUCCESS,
    DANGER,
    WARNING
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    theme: SovereignThemeConfig,
    intent: ButtonIntent = ButtonIntent.NEUTRAL,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val containerColor = when (intent) {
        ButtonIntent.NEUTRAL -> theme.surface
        ButtonIntent.SUCCESS -> theme.successColor
        ButtonIntent.DANGER -> theme.dangerColor
        ButtonIntent.WARNING -> theme.warningColor
    }

    val textColor = if (intent == ButtonIntent.NEUTRAL) theme.textMain else theme.background

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor,
            disabledContainerColor = theme.disabledSurface,
            disabledContentColor = theme.disabledText
        ),
        shape = RoundedCornerShape(theme.cornerRadius),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontFamily = theme.fontPrimary
        )
    }
}