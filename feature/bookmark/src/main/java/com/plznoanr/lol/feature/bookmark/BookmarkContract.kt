package com.plznoanr.lol.feature.bookmark

import androidx.compose.runtime.Stable
import com.plznoanr.lol.core.model.Summoner
import kotlinx.collections.immutable.PersistentList

sealed interface BookmarkUiState {

    @Stable
    data class Success(
        val data: PersistentList<Summoner>
    ) : BookmarkUiState

    data object Loading : BookmarkUiState

    data class Error(val error: String?) : BookmarkUiState

}