package com.mobiledev.gradecalculator.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mobiledev.gradecalculator.enums.Error
import com.mobiledev.gradecalculator.ui.theme.Green
import com.mobiledev.gradecalculator.ui.theme.Purple

@Composable
fun TextField(
    input: String,
    label: String,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String = Error.INVALID_INPUT.message
) {
    var message by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .then(modifier),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                autoCorrect = true
            ),
            label = { Text(text = label) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (isValid) Color.Black else Color.Red,
                errorLabelColor = Color.Red,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = if (isValid) Green else Color.Red,
                unfocusedIndicatorColor = if (isValid) Color.Gray else Color.Red,
                disabledIndicatorColor = Color.LightGray
            ),
            trailingIcon = {
                when (trailingIcon) {
                    null -> {
                        if (input.isNotBlank()) {
                            IconButton(
                                onClick = { onValueChange("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    tint = Purple,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    else -> {
                        trailingIcon.invoke()
                    }
                }
            },
            value = input,
            onValueChange = { change -> onValueChange(change) }
        )

        message = when (isValid) {
            true -> ""
            false -> errorMessage
        }

        Text(
            text = message,
            color = Color.Red,
            style = MaterialTheme.typography.caption
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}