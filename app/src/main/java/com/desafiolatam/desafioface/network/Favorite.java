package com.desafiolatam.desafioface.network;

import com.desafiolatam.desafioface.models.Developer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Pedro on 01-08-2017.
 */

public interface Favorite {

    @FormUrlEncoded
    @PUT("users/fcm_token")
    Call<String> putFcmToken(@Field("users[fcm_token]") String fcmToken);

    @POST("users/{id}/favorite")
    Call<Developer> postFavorite(@Path("id") long id);
}
