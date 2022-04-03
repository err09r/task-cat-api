package com.app.catapi.di

import com.app.catapi.data.paging.CatPagingSource
import com.app.catapi.presentation.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<HomeViewModel> {
        HomeViewModel(
            pagingSource = get()
        )
    }

    single<CatPagingSource> {
        CatPagingSource(api =  get())
    }

    single<Picasso> {
        Picasso.get()
    }
}