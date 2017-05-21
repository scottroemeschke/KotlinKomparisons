package com.smeschke.kotlinkomparisons.kt

import com.jakewharton.rxrelay2.BehaviorRelay
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by Scott on 5/21/2017.
 */
interface LoginDetailsRepo {
    fun observeLoginDetails(): Observable<LoginDetails>
    fun observePreferredLoginMethod(): Observable<LoginMethod>
    fun updateLoginDetails(details: LoginDetails): Completable
    fun updateLoginMethod(method: LoginMethod): Completable
}

class PersistedLoginDetailsRepo(internal val persistence: RxPaperBook): LoginDetailsRepo {

    internal object PersistenceKeys {
        val details = "details"
        val method = "method"
    }

    internal val detailsRelay = BehaviorRelay.create<LoginDetails>()
    internal val methodRelay = BehaviorRelay.create<LoginMethod>()

    override fun observeLoginDetails(): Observable<LoginDetails> {
        return detailsRelay.withInitIfEmpty { persistence.read(PersistenceKeys.details, LoginDetails.empty) }
    }

    override fun observePreferredLoginMethod(): Observable<LoginMethod> {
        return methodRelay.withInitIfEmpty {  persistence.read(PersistenceKeys.method, LoginMethod.UNKNOWN) }
    }

    override fun updateLoginDetails(details: LoginDetails): Completable {
        return persistence.writeThenForward(PersistenceKeys.details, details, detailsRelay)
    }

    override fun updateLoginMethod(method: LoginMethod): Completable {
        return persistence.writeThenForward(PersistenceKeys.method, method, methodRelay)
    }

}


