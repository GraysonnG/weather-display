package com.blanktheevil.violetnotes

import android.app.Application
import com.blanktheevil.violetnotes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VioletNotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@VioletNotesApplication)
            modules(appModule)
        }
    }
}