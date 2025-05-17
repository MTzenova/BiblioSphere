package com.example.bibliosphere.presentation.components

import android.text.Html
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

@Composable
fun ExpandingText(
    description: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    expandable: Boolean = true,
    collapsedMaxLines: Int = 5,
    expandedMaxLines: Int = Int.MAX_VALUE,
) {
    val text =
        Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)

    var canTextExpand by remember(text) { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier.animateContentSize(animationSpec = spring())) {
        Text(
            text = text.toString(),
            style = textStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = if (expanded) expandedMaxLines else collapsedMaxLines,
//            modifier = Modifier
//                .clickable(
//                    enabled = expandable && canTextExpand,
//                    onClick = { expanded = !expanded },
//                )
//                .animateContentSize(animationSpec = spring())
//                .then(modifier),
            onTextLayout = {
                if (!expanded) {
                    canTextExpand = it.hasVisualOverflow
                }
            }
        )
        if(canTextExpand && expandable) {
            Text(
                text = if(expanded){
                    "Leer menos"
                } else{
                    "Leer más"
                },
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clickable{expanded = !expanded}
                    .padding(top = 4.dp)
            )
        }
    }

}