package com.desafiolatam.desafioface.network;

import com.desafiolatam.desafioface.models.Developer;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Pedro on 18-07-2017.
 */

public interface Users {
    /*@Headers({
            "authtoken: _SdgtSFsyRoJ5yznsYs4",
            "Accept: application/json",
            "Source: mobile"
    })*/
    @GET("users")
    Call<Developer[]> getUsers(@QueryMap Map<String, String> queryParams);

    //Call<*Lo que recibe*> nombreFuncion(*Queries*);
}
