package com.sion.lazybones

import android.app.Application
import timber.log.Timber


class LazybonesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("[onCreate]")
    }
}