package com.guido.booksexercise.data

data class Books(val id: String,
                 val etag: String,
                 val title: String,
                 val authors: List<String>,
                 val publisher: String?,
                 val publishedDate: String,
                 val description: String?,
                 val pageCount: Long,
                 val categories: List<String>?,
                 val smallThumbnail: String?,
                 val thumbnail: String?,
                 val updated: String?)
