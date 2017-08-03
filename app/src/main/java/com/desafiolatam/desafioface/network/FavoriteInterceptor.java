package com.desafiolatam.desafioface.network;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.models.Developer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pedro on 01-08-2017.
 */

public class FavoriteInterceptor {

    public static final String BASE_URL = "https://empieza.desafiolatam.com/";

    public Favorite put() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("authtoken", CurrentUser.listAll(CurrentUser.class).get(0).getAuth_token())
                        .header("Source", "mobile")
                        .build();

                Response response = chain.proceed(request);

                return response;
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Favorite service = interceptor.create(Favorite.class);
        return service;
    }

}

