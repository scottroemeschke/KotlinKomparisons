package com.smeschke.kotlinkomparisons.kt

import com.jakewharton.rxrelay2.BehaviorRelay
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer

/**
 * Created by Scott on 5/21/2017.
 */
inline fun <T> BehaviorRelay<T>.withInitIfEmpty(initializer: () -> Single<T>): Observable<T> {
    if (hasValue()) return hide()
    else return initializer.invoke().flatMapObservable {
        accept(it)
        hide()
    }
}

fun <T> RxPaperBook.writeThenForward(key: String, value: T, consumer: Consumer<T>): Completable {
    return write(key, value).doOnComplete { consumer.accept(value) }
}