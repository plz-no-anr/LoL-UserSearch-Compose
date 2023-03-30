package com.plznoanr.domain.extentions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T>Result<T>.asFlow(): Flow<Result<T>> = flowOf(this)

fun printLog(
    useCaseName: String,
    parameter: String,
    result: String
) {
    decoration("LOG-START")
    println("UseCaseName: $useCaseName")
    println("Parameter: $parameter")
    println("Result: $result")
    decoration("LOG-END")
}

private fun decoration(msg: String) = run {
    print(msg)
    for (i in 0..100) {
        print("-")
    }
    println()
}


