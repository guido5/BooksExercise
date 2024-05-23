package com.guido.booksexercise.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.guido.booksexercise.data.retrofitRepository.VolumesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(app: Application, val volumesRepository: VolumesRepository) : AndroidViewModel(app) {


    fun getVolumes() {
        viewModelScope.launch {
            volumesRepository.getVolumes("harry potter").collect {
                println(it.size)
            }
        }
    }
}