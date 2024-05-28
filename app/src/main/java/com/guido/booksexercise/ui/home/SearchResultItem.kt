package com.guido.booksexercise.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guido.booksexercise.data.Books
import com.guido.booksexercise.ui.theme.BooksExerciseTheme

@Preview
@Composable
fun BooksSearchItemPreview() {
    BooksSearchItem(
        Books("1", "sdaf",
            "Hola mundo", listOf("asdf", "asdgf"),
            "Publicadora", "2025", "alguna descripcion",
            250, listOf(""), "gsdfg", "dsgdf", "now")
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BooksSearchItem(books: Books) {
    BooksExerciseTheme {
        Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    GlideImage(
                        model = books.smallThumbnail,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        text = books.title,
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(2.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    books.description?.let {
                        Text(
                            text = it,
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(2.dp),
                            textAlign = TextAlign.Justify,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp), Arrangement.Center
                    ) {
                        var authorsConcat = ""
                        books.authors.forEach {
                            authorsConcat = "$it, $authorsConcat"
                        }

                        Text(
                            text = authorsConcat,
                            Modifier
                                .width(50.dp)
                                .fillMaxHeight(), textAlign = TextAlign.Left
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = books.publishedDate,
                            Modifier
                                .width(50.dp)
                                .fillMaxHeight(), textAlign = TextAlign.Right
                        )
                    }
                }
            }
        }

    }
}