package com.desafiolatam.desafioface.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.desafiolatam.desafioface.app.DesafioApp;
import com.desafiolatam.desafioface.models.Developer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pedro on 18-07-2017.
 */

public class GetUsers extends AsyncTask<Map<String, String>, Integer, Integer> {

    private int additionalPages;
    private Map<String, String> queryMap;
    private int result;
    private final Users request = new UsersInterceptor().get();
    private Context context = null;

    /*public GetUsers(int additionalPages) {
            this.additionalPages = additionalPages;
        }*/

    public GetUsers(int additionalPages, Context context) {
        this.additionalPages = additionalPages;
        this.context = context;
    }

    @Override
        protected Integer doInBackground(Map<String, String>... params) {
            queryMap = params[0];
            if (additionalPages < 0) {
                while (200 == connect()) {
                    increasePage();
                }
            } else {
                while (additionalPages >= 0) {
                    additionalPages--;
                    connect();
                    increasePage();
                }

            }
            return result;
        }


        private void increasePage() {
            int page = Integer.parseInt(queryMap.get("page"));
            page++;
            queryMap.put("page", String.valueOf(page));
        }

        private int connect() {
            int code = 666;
            Call<Developer[]> call = request.getUsers(queryMap);
            try {
                Response<Developer[]> response = call.execute();
                code = response.code();
                if (200 == code && response.isSuccessful()) {
                    Developer[] developers = response.body();
                    if (developers != null && developers.length > 0) {
                        Log.d("DEVELOPERS", String.valueOf(developers.length));
                        for (Developer serverDev : developers) {
                            List<Developer> localDevs = Developer.find(Developer.class, "serverId = ? ", String.valueOf(serverDev.getId()));
                            if (localDevs != null && localDevs.size() > 0) {
                                Developer local = localDevs.get(0);
                                local.setEmail(serverDev.getEmail());
                                local.setPhoto_url(serverDev.getPhoto_url());
                                local.save();
                            } else {
                                serverDev.create();
                            }
                        }
                    } else {
                        code = 777;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                code = 888;
            }
            result = code;

            return result;
        }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if (context != null && integer == 500)
        {
            Intent intent = new Intent();
            intent.setAction(DesafioApp.EXPIRED_SESSION);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}
