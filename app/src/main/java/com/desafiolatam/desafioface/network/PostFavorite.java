package com.desafiolatam.desafioface.network;

import android.os.AsyncTask;

import com.desafiolatam.desafioface.models.Developer;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pedro on 01-08-2017.
 */

public class PostFavorite extends AsyncTask<Long, Integer, Integer> {
    @Override
    protected Integer doInBackground(Long... params) {
        long id = params[0];
        Favorite favorite = new FavoriteInterceptor().put();

        Call<Developer> call = favorite.postFavorite(id);
        try {
            Response<Developer> response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
