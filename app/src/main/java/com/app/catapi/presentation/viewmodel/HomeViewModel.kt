package com.app.catapi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.catapi.data.mappers.CatMapper
import com.app.catapi.data.paging.CatPagingSource
import com.app.catapi.model.CatItem
import com.app.catapi.util.constants.Constants.DEFAULT_PAGE_SIZE
import com.app.catapi.util.constants.Constants.INITIAL_KEY
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val pagingSource: CatPagingSource) : ViewModel() {

    val data: StateFlow<PagingData<CatItem>> =
        Pager(PagingConfig(pageSize = DEFAULT_PAGE_SIZE), initialKey = INITIAL_KEY) {
            pagingSource
        }.flow.map { pagingData ->
            pagingData.map { CatMapper.mapToDomainModel(it) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}