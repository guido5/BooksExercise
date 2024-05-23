package com.guido.booksexercise.data.roomRepository

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.guido.booksexercise.data.Books
import com.guido.booksexercise.data.retrofitRepository.VolumesRepository

@Entity
data class VolumesEntity(
                        @PrimaryKey val idVolumes: String,
                         val etag: String,
                         val title: String,
                         val publisher: String,
                         val publishedDate: String,
                         val description: String,
                         val pageCount: Long,
                         val smallThumbnail: String,
                         val thumbnail: String,
                         val updated: String)

@Entity
data class AuthorsEntity(@PrimaryKey val authorId: Int,
                         val name: String)

@Entity(primaryKeys = ["idVolumes", "authorId"])
data class VolumesAuthorsCrossRef(val idVolumes: String, val authorId: Int  )

data class VolumesWithAuthor(
 @Embedded val volumesEntity: VolumesEntity,
 @Relation(
  parentColumn = "idVolumes",
  entityColumn = "authorId",
  associateBy = Junction(VolumesAuthorsCrossRef::class)
 ) val authors: List<AuthorsEntity>
)
