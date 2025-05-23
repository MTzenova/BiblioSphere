package com.example.bibliosphere.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.bibliosphere.data.model.TabDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarNavigation(
    modifier: Modifier = Modifier,
    onTabSelected: (Int, TabDestination) -> Unit,
    destinations: List<TabDestination>,
    selectedIndex: Int,
){
    PrimaryTabRow(
        modifier = modifier,
        selectedTabIndex = selectedIndex,
    ) {
        destinations.forEachIndexed { index, item ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onTabSelected(index, item) },
                text = {
                    Text(text = item.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            )
        }
    }
}
