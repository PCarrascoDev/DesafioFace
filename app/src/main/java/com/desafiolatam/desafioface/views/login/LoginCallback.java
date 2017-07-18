package com.desafiolatam.desafioface.views.login;

/**
 * Created by Pedro on 10-07-2017.
 */

public interface LoginCallback {
    void success();
    void emailFormat();
    void requiredFields();
    void credentials();
}