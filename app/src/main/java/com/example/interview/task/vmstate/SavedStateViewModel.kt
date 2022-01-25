package com.example.interview.task.vmstate

import androidx.lifecycle.*


class DataRepository {
    fun getFilteredData(query: String): LiveData<List<String>> {
        return MutableLiveData(emptyList())
    }
}

/**
 * What is the point of using SavedStateHandle?
 */
class SavedStateViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: DataRepository,
) : ViewModel() {

    val filteredData: LiveData<List<String>> =
        savedStateHandle.getLiveData<String>("query")
            .switchMap { repository.getFilteredData(it) }


    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }
}

