package com.desafiolatam.desafioface.network;

import android.os.AsyncTask;

import com.desafiolatam.desafioface.models.Developer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pedro on 01-08-2017.
 */

public class PutFcmToken extends AsyncTask<String, Integer, Integer> {

    private Favorite favorite = new FavoriteInterceptor().put();

    @Override
    protected Integer doInBackground(String... fcmToken) {
        Integer code = 666;
        Call<String> call = favorite.putFcmToken(fcmToken[0]);
        try {
            Response<String> response = call.execute();
            code = response.code();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
