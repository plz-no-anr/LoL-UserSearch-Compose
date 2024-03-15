package com.plznoanr.lol.core.common.di

import javax.inject.Qualifier

enum class AppDispatcher {
    Default,
    IO,
    Main
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Dispatcher(val type: AppDispatcher)