package com.linkedintools.rx

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

/**
 * Exponential backoff that respects the equation: delay * retries ^ 2 * jitter
 * See: https://leandrofavarin.com/exponential-backoff-rxjava-operator-with-jitter
 */
class ExpBackoff(
    private val jitter: Jitter,
    private val delay: Long,
    private val unit: TimeUnit,
    private val retries: Int = 0
) : io.reactivex.functions.Function<Observable<out Throwable>, Observable<Long>> {

    @Throws(Exception::class)
    override fun apply(observable: Observable<out Throwable>): Observable<Long> {
        return observable
            .zipWith(Observable.range(1, retries + 1), BiFunction<Throwable, Int, Int> { _, retryCount ->
                retryCount
            })
            .flatMap { attemptNumber ->
                if (attemptNumber == retries)
                    throw java.lang.Exception()
                else
                    Observable.timer(getNewInterval(attemptNumber), unit)
            }
    }

    private fun getNewInterval(retryCount: Int): Long {
        var newInterval = (delay * Math.pow(retryCount.toDouble(), 2.0) * jitter.get()).toLong()
        if (newInterval < 0) {
            newInterval = Long.MAX_VALUE
        }
        return newInterval
    }
}