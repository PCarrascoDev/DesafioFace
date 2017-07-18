package com.desafiolatam.desafioface.views.splash;

import com.desafiolatam.desafioface.models.CurrentUser;

/**
 * Created by Pedro on 04-07-2017.
 */

public class LoginValidation {

    private LoginCallback callback;

    public LoginValidation(LoginCallback callback){this.callback = callback;}

    public void isLogged()
    {
        if (CurrentUser.listAll(CurrentUser.class).size() > 0)
        {
            callback.logged();
        }
        else
        {
            callback.login();
        }
    }
}
