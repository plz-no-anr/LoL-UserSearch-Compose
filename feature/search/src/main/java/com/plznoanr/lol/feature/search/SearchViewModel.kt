package com.plznoanr.lol.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plznoanr.lol.core.domain.usecase.search.DeleteAllSearchUseCase
import com.plznoanr.lol.core.domain.usecase.search.DeleteSearchUseCase
import com.plznoanr.lol.core.domain.usecase.search.GetRecentSearchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentSearchListUseCase: GetRecentSearchListUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteAllSearchUseCase
): ViewModel() {

    private fun deleteSearch(name: String) {
        viewModelScope.launch {
            deleteSearchUseCase(name)
        }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            deleteSearchAllUseCase()
        }
    }

}