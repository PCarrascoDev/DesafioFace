package com.desafiolatam.desafioface.views.login;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.network.Session;
import com.desafiolatam.desafioface.network.SessionInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pedro on 10-07-2017.
 */

public class Login {

    private LoginCallback callback;

    public Login(LoginCallback callback) {
        this.callback = callback;
    }

    public void session(String email, String password)
    {
        if (email.contains("@") && email.contains("."))
        {
            if (email.trim().length() > 0 && password.trim().length() > 0)
            {
                Session session = new SessionInterceptor().get();
                Call<CurrentUser> call = session.login(email, password);
                call.enqueue(new Callback<CurrentUser>() {
                    @Override
                    public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                        if(200 == response.code() && response.isSuccessful())
                        {
                            CurrentUser currentUser = response.body();
                            currentUser.create();
                            callback.success();
                        }
                        else
                        {
                            callback.credentials();
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentUser> call, Throwable t) {

                    }
                });
            }
            else
            {
                callback.requiredFields();
            }
        }
        else
        {
            callback.emailFormat();
        }

    }
}
