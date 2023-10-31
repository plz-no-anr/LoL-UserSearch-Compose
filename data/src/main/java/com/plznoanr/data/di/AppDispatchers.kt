package com.plznoanr.data.di

import javax.inject.Qualifier

object AppDispatchers {

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Default

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IO

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Main

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

}