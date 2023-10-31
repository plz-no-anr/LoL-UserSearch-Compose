package com.plznoanr.lol.utils

import timber.log.Timber

class TimberDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "[${Thread.currentThread().name}]${element.fileName}:${element.lineNumber}#${element.methodName}"
    }
}