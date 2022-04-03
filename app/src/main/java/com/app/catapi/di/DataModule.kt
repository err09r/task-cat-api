package com.app.catapi.di

import com.app.catapi.data.remote.api.CatApi
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single<CatApi> {
        val retrofit: Retrofit = get()
        retrofit.create(CatApi::class.java)
    }
}