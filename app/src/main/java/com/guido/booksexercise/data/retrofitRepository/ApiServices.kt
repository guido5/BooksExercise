package com.guido.booksexercise.data.retrofitRepository

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("volumes")
    suspend fun getVolumes(@Query("q") title: String, @Query("maxResults") maxResults: Int) : VolumesDTO

}
