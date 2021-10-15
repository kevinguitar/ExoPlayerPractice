package com.example.exoplayerpractice.utils

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

/**
 * Maps value of one StateFlow to another StateFlow
 *
 * [transform] is not a suspend function because it required for initial value.
 *
 * Careful with side effects when use this function, [transform] will be called øn every access to original value
 * So every time when you call [StateFlow.value] it will call [transform]
 */
fun <T, R> StateFlow<T>.mapState(transform: (T) -> R): StateFlow<R> {
    return MappedStateFlow(this, transform)
}

private class MappedStateFlow<T, R>(private val source: StateFlow<T>, private val transform: (T) -> R) : StateFlow<R> {

    override val value: R
        get() = transform(source.value)

    override val replayCache: List<R>
        get() = source.replayCache.map(transform)

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<R>) {
        source.collect(object : FlowCollector<T> {
            override suspend fun emit(value: T) {
                collector.emit(transform(value))
            }
        })
    }
}

