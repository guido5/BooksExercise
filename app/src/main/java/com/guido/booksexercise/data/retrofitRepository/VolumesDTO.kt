package com.guido.booksexercise.data.retrofitRepository

import com.guido.booksexercise.data.Books

data class VolumesDTO(val items: List<Item>,)

data class Item(
    val kind: String,
    val id: String,
    val etag: String,
    val volumeInfo: VolumeInfo,
    val userInfo: UserInfo?,
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String?,
    val publishedDate: String,
    val description: String?,
    val pageCount: Long,
    val categories: List<String>?,
    val imageLinks: ImageLinks?,
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
)

data class UserInfo(
    val updated: String,
)
fun VolumesDTO.toDomain() : List<Books> {
    return items.map {
        Books(it.id,
            it.etag,
            it.volumeInfo.title,
            it.volumeInfo.authors,
            it.volumeInfo.publisher,
            it.volumeInfo.publishedDate,
            it.volumeInfo.description,
            it.volumeInfo.pageCount,
            it.volumeInfo.categories,
            it.volumeInfo.imageLinks?.smallThumbnail,
            it.volumeInfo.imageLinks?.thumbnail,
            it.userInfo?.updated)
    }
}
