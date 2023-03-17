package com.plz.no.anr.lol_usersearch_compose.utils

import timber.log.Timber

class TimberDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.fileName}:${element.lineNumber}#${element.methodName}"
    }
}