package com.example.bibliosphere.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R

@Composable
fun GenreList(
    genreSelected: String?,
    onGenreSelected: (String?) -> Unit,
){
    var expanded by remember { mutableStateOf(false) }

    val genreList = genreBooksList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
            .animateContentSize()
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = stringResource(R.string.filter_by_genre))

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription =
                        if(expanded){
                            stringResource(R.string.show_less)
                        }else{
                            stringResource(R.string.show_more)
                        }
                )
            }

            //quita el genero seleccionado
            Text(text = stringResource(R.string.clear_filters),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.clickable {
                    onGenreSelected(null.toString())
                    expanded = false
                }
            )
        }
        if (expanded) {
            FlowRow(modifier = Modifier
                .padding(top = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                genreList.forEach { genre ->
                    AssistChip(
                        onClick = {
                            onGenreSelected(genre)
                            expanded = false
                        },
                        label = {Text(text = genre)},
                        colors = if (genre == genreSelected){
                            AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        }else{
                            AssistChipDefaults.assistChipColors()
                        }
                    )
                }
            }
        }
    }
}

fun genreBooksList(): List<String> {
    return listOf(
        "Fantasy",
        "Adventure",
        "Thriller",
        "Drama",
        "Science Fiction",
        "Horror",
        "Romance",
        "Mystery",
        "Poetry",
        "Biography",
        "Science",
        "Fiction",
        "Art",
        "Health",
        "Juvenile Fiction",
        "Epic",
        "Action & Adventure"
    )
}