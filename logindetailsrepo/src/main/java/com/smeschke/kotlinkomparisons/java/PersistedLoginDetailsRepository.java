package com.smeschke.kotlinkomparisons.java;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.pacoworks.rxpaper2.RxPaperBook;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Scott on 5/21/2017.
 */

public final class PersistedLoginDetailsRepository implements LoginDetailsRepository {

    private static final String PREVIOUS_EMAIL_KEY = "prev_email";
    private static final String LOGIN_METHOD = "login_method";

    private final RxPaperBook persistence;
    private final BehaviorRelay<LoginDetails> detailsRelay = BehaviorRelay.create();
    private final BehaviorRelay<LoginMethod> methodRelay = BehaviorRelay.create();

    public PersistedLoginDetailsRepository(final @NonNull RxPaperBook persistence) {
        this.persistence = checkNotNull(persistence);
    }

    @Override
    public @NonNull Observable<LoginDetails> observeLoginDetails() {
        if (detailsRelay.hasValue()) return detailsRelay.hide();
        else {
            return persistence.read(PREVIOUS_EMAIL_KEY, LoginDetails.empty())
                    .flatMapObservable(new Function<LoginDetails, ObservableSource<? extends LoginDetails>>() {
                        @Override
                        public ObservableSource<? extends LoginDetails> apply(@NonNull LoginDetails email) throws Exception {
                            detailsRelay.accept(email);
                            return detailsRelay.hide();
                        }
                    });
        }
    }

    @Override
    public @NonNull Observable<LoginMethod> observePreferredLoginMethod() {
        if (detailsRelay.hasValue()) return methodRelay.hide();
        else {
            return persistence.read(LOGIN_METHOD, LoginMethod.UNKNOWN)
                    .flatMapObservable(new Function<LoginMethod, ObservableSource<? extends LoginMethod>>() {
                        @Override
                        public ObservableSource<? extends LoginMethod> apply(@NonNull LoginMethod method) throws Exception {
                            PersistedLoginDetailsRepository.this.methodRelay.accept(method);
                            return PersistedLoginDetailsRepository.this.methodRelay.hide();
                        }
                    });
        }
    }

    @Override
    public @NonNull Completable updateLoginDetails(@NonNull final LoginDetails email) {
        checkNotNull(email);
        return persistence.write(PREVIOUS_EMAIL_KEY, email)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        detailsRelay.accept(email);
                    }
                });
    }

    @Override
    public @NonNull Completable updateLoginMethod(@NonNull final LoginMethod method) {
        checkNotNull(method);
        return persistence.write(LOGIN_METHOD, method)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        methodRelay.accept(method);
                    }
                });
    }
}

