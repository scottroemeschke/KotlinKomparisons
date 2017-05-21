package com.smeschke.kotlinkomparisons.java;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import io.reactivex.annotations.Nullable;

/**
 * Created by Scott on 5/21/2017.
 */

public final class LoginDetails {

    private final String email;
    private final String username;

    public LoginDetails(@Nullable final String email, @Nullable final String username) {
        this.email = email;
        this.username = username;
    }

    public @Nullable String getEmail() {
        return email;
    }

    public @Nullable String getUsername() {
        return username;
    }

    public boolean isPresent(){
        return email != null && username != null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("username", username)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDetails that = (LoginDetails) o;
        return Objects.equal(email, that.email) &&
                Objects.equal(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email, username);
    }

    public static LoginDetails empty(){
        return new LoginDetails(null,null);
    }
}
