package com.plznoanr.lol.core.network.di.qualifiers

import javax.inject.Qualifier

/**
 * Retrofit EndPoint Qualifier
 */
enum class EndPointType {
    Riot,
    Lol
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EndPoint(val type: EndPointType)