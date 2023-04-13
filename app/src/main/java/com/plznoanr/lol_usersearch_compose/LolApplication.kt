package com.plznoanr.lol_usersearch_compose

import android.app.Application
import com.plznoanr.lol_usersearch_compose.utils.TimberDebugTree
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