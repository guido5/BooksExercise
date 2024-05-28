package com.guido.booksexercise.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.guido.booksexercise.data.Books
import com.guido.booksexercise.data.retrofitRepository.VolumesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(app: Application, val volumesRepository: VolumesRepository) : AndroidViewModel(app) {


    val _searchedItems =  MutableStateFlow<List<Books>>(listOf())
    val searchedItems = _searchedItems.asStateFlow()

    fun getVolumes(title : String) {
        viewModelScope.launch {
            volumesRepository.getVolumes(title).collect {
                _searchedItems.value = it
            }
        }
    }
}
