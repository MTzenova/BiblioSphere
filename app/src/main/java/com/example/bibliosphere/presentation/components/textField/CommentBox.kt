package com.example.bibliosphere.presentation.components.textField

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.presentation.firebase.CommentData
import com.example.bibliosphere.R

@Composable
fun CommentBox(
    bookId: String,
    userId: String,
    userName: String,
    userImage: Int,
    comments: List<CommentData>,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .imePadding()) {

        Text(text = stringResource(R.string.comments), style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.CenterHorizontally))

        LazyColumn(modifier = Modifier
            .fillMaxHeight(0.5f)
            .padding(top = 8.dp)) {
            items(comments.size) { i ->
                val comment = comments[i]

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.Start
                ) {
                    val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
                        ImageRequest.Builder(LocalContext.current)
                            .data(comment.userImage)
                            .size(252)
                            .build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = stringResource(R.string.profile_picture, comment.userName),
                        modifier = Modifier
                            .size(74.dp)
                            .clip(RoundedCornerShape(42.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                    )
                    {
                        Text(text = formatTimestamp(comment.timestamp), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = comment.userName, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = comment.comment, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextInputFieldComment(
            label = stringResource(R.string.write_comment),
            value = text.value,
            onValueChange = { text.value = it },
            onImeAction = {val commentText:String = text.value.trim()
                if(commentText.isNotEmpty()){
                    onSend(commentText)
                    text.value = ""
                }}

        )
    }

}