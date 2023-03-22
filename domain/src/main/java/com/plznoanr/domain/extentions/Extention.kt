package com.plznoanr.domain.extentions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T>Result<T>.asFlow(): Flow<Result<T>> = flowOf(this)

fun String.log() {
    decoration("LOG")
    this
        .split("/")
        .forEachIndexed { index, s ->
            when (index) {
                0 -> println("UseCaseName: $s")
                1 -> println("Parameter: $s")
                2 -> println("Result: $s")
            }
        }
    decoration("END")
}

private fun decoration(msg: String) = run {
    print(msg)
    for (i in 0..100) {
        print("-")
    }
    println()
}