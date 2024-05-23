package com.guido.booksexercise.data.retrofitRepository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guido.booksexercise.data.Books
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class VolumesRepository @Inject constructor(val services: ApiServices) {

   fun getVolumes(title: String) : Flow<List<Books>> = flow {
       val myVolumesDTO = services.getVolumes(title, 10)
       val myVolumes = myVolumesDTO.toDomain()
       emit(myVolumes)
   }
}