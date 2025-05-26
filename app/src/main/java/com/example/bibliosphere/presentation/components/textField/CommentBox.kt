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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bibliosphere.presentation.firebase.CommentData
import org.w3c.dom.Comment

@Composable
fun CommentBox(bookId:String, userId:String, userName:String, userImage:Int, comments:List<CommentData>, onSend:(String) -> Unit) {
    val text = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {



        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text(text = "Enter comment") },
            placeholder = { Text(text = "Type a comment") },
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = {
                val commentText:String = text.value.trim()
                if(commentText.isNotEmpty()){
                    onSend(commentText)
                    text.value = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Enviar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Comentarios:", style = MaterialTheme.typography.titleMedium)

        LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
            items(comments.size) { i ->
                val comment = comments[i]

                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val painter = rememberAsyncImagePainter( //hago esto para que las imagenes no pesen tanto
                        ImageRequest.Builder(LocalContext.current)
                            .data(comment.userImage)
                            .size(252)
                            .build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = "${comment.userName} profile picture",
                        modifier = Modifier.size(84.dp).clip(RoundedCornerShape(42.dp))
                    )

                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {

                        Text(text = comment.userName, style = MaterialTheme.typography.bodyMedium)
                        Text(text = comment.comment, style = MaterialTheme.typography.bodyMedium)
                    }
                }


            }
        }

    }

}