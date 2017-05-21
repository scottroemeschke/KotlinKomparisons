package com.smeschke.kotlinkomparisons.java;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by Scott on 5/21/2017.
 */

interface LoginDetailsRepository {
    @NonNull Observable<LoginDetails> observeLoginDetails();
    @NonNull Observable<LoginMethod> observePreferredLoginMethod();
    @NonNull Completable updateLoginDetails(@NonNull LoginDetails details);
    @NonNull Completable updateLoginMethod(@NonNull LoginMethod method);
}
