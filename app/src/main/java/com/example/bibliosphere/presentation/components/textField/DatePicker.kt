package com.example.bibliosphere.presentation.components.textField

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//este es el malo
fun DatePicker() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text(stringResource(R.string.dob_label)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.select_date)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(
    modifier: Modifier = Modifier,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    isValidDate: Boolean,
    enabled: Boolean,
    isProfile:Boolean? = null,
    leadingIcon: ImageVector? = null,
) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = birthDate,
        onValueChange = { },
        label = {
            Text(
                text = stringResource(R.string.dob_label),
                color = if (isProfile == true)
                    MaterialTheme.colorScheme.background
                else
                    MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )
        },
        readOnly = true,
        enabled = enabled,
        placeholder = { Text(stringResource(R.string.dob_placeholder),  color = MaterialTheme.colorScheme.onBackground) },
        shape = RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeExtraLarge),
        leadingIcon = {
            IconButton(
                onClick = {
                    if (enabled) showModal = true
                },
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.select_date),
                    tint = if(enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface
                )
            }
        },
//        trailingIcon = {
//            if(isProfile != true){
//                Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.select_date))
//            }
//        },
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Start,
            color = if(enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            cursorColor = MaterialTheme.colorScheme.secondary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.error,

            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,

            // 🎯 Aquí lo importante:
            disabledContainerColor = if (isProfile == true) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant,

            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            errorBorderColor = MaterialTheme.colorScheme.error,
        ),
        isError = !isValidDate && birthDate.isNotEmpty(),
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if(!isValidDate && birthDate.isNotEmpty()) {
        Text(
            text = stringResource(R.string.dob_error),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = BiblioSphereTheme.dimens.paddingMedium, top = BiblioSphereTheme.dimens.paddingSmall)
        )
    }

    if (showModal && enabled) { //para editar/leer
        DatePickerModal(
            onDateSelected = { date ->
                selectedDate = date
                if(date!=null) {
                    val format = convertMillisToDate(date)
                    onBirthDateChange(format)
                }
            },
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formatter.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayedMonthMillis = System.currentTimeMillis(),
        yearRange = 1900..2025
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
