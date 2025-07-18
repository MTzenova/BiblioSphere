package com.example.bibliosphere.presentation.bookDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.components.BookDescription
import com.example.bibliosphere.presentation.components.BookDetailCard
import com.example.bibliosphere.presentation.components.buttons.CustomButton
import com.example.bibliosphere.presentation.components.textField.CommentBox
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(bookId: String, viewModel: BookDetailScreenViewModel) {
    val bookDetail by viewModel.bookDetail.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val userImage by viewModel.imageResId.collectAsState()
    val userName by viewModel.userName.collectAsState()

    val comments by viewModel.comments.collectAsState()
    val bookState by viewModel.booksState.collectAsState()

    var showBottomSheet by remember {mutableStateOf(false)}
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(bookId) {
        viewModel.loadBookDetail(bookId)
        viewModel.getComments(bookId)
        if (userId != null) {
            viewModel.getUserData(userId)
        }
        if(showBottomSheet)sheetState.expand() //para que cuando se abra el BottomSheet, se expanda para ver la caja de comentarios
    }

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else if(errorMessage != null) {
        Text(text = errorMessage!!)
    }else{
        bookDetail?.let {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                BookDetailCard(
                    author = it.volumeInfo?.authors?.joinToString(", ") ?: stringResource(R.string.unknown_author),
                    title = it.volumeInfo?.title?:stringResource(R.string.unknown_title),
                    image = it.volumeInfo?.imageLinks?.thumbnail?:"",
                    type = it.volumeInfo?.categories?.joinToString(", ") ?: stringResource(R.string.no_category),
                    initialStates = bookState,
                    onStatesChanged = { newState ->
                        if(newState.isEmpty()) {
                            viewModel.deleteBookFromLibrary(bookId)
                        }else{
                            viewModel.updateBookState(newState,bookId)
                        }

                    },
                    averageRating = it.volumeInfo?.averageRating?:0f,
                )
                Text(text = stringResource(R.string.description), modifier = Modifier.padding(horizontal = 15.dp), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(10.dp))
                BookDescription(
                    description =  it.volumeInfo?.description ?: stringResource(R.string.no_description)
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(
                    text = stringResource(R.string.show_comments),
                    onTap = { showBottomSheet = !showBottomSheet },
                    modifier = Modifier.padding(horizontal = 15.dp),
                    textColor = MaterialTheme.colorScheme.background,
                    buttonColor = MaterialTheme.colorScheme.onBackground,
                )
                //Text(text = "Mostrar comentarios", modifier = Modifier.padding(horizontal = 15.dp).clickable { showBottomSheet = !showBottomSheet })
            }
        }
    }

    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.imePadding()
        ) {
            if (userId != null) {
                CommentBox(
                    bookId = bookId,
                    userId = userId,
                    userName = userName,
                    userImage = userImage,
                    comments = comments,
                    onSend = { commentText ->
                        viewModel.sendComment(bookId, commentText, userName, userImage)
                    },
                    modifier = Modifier.imePadding().fillMaxHeight(0.9f)
                )
            }
        }
    }
}