package com.app.catapi.app

import android.app.Application
import com.app.catapi.di.appModule
import com.app.catapi.di.dataModule
import com.app.catapi.di.networkModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(appModule, networkModule, dataModule))
            androidLogger(level = Level.DEBUG)
        }
    }
}