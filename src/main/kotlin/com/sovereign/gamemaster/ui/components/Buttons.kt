package com.sovereign.gamemaster.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

enum class ButtonIntent {
    NEUTRAL,
    SUCCESS,
    DANGER,
    WARNING
}

@Composable
fun CustomButton(
    text: String, // String param for button label
    onClick: () -> Unit, // action callback
    theme: SovereignThemeConfig, // inject theme object
    intent: ButtonIntent = ButtonIntent.NEUTRAL,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (intent) {
        ButtonIntent.NEUTRAL -> theme.surface
        ButtonIntent.SUCCESS -> theme.successColor
        ButtonIntent.DANGER -> theme.dangerColor
        ButtonIntent.WARNING -> theme.warningColor
    }

    val textColor = if (intent == ButtonIntent.NEUTRAL) theme.textMain else theme.background

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = RoundedCornerShape(theme.cornerRadius),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = textColor,
            fontFamily = theme.fontPrimary)
    }
}