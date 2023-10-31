package com.plznoanr.lol

import android.app.Application
import com.plznoanr.lol.utils.TimberDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class LolApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }
}