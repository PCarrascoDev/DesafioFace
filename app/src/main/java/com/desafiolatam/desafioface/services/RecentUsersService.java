package com.desafiolatam.desafioface.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.desafiolatam.desafioface.network.GetUsers;

import java.util.HashMap;
import java.util.Map;


public class RecentUsersService extends IntentService {
    private static final String UPDATE = "com.desafiolatam.desafioface.services.action.UPDATE";
    public static final String UPDATE_USERS = "com.desafiolatam.desafioface.services.action.UPDATE_USERS";

    private class UpdateUsers extends GetUsers{


        public UpdateUsers(int additionalPages, Context context) {
            super(additionalPages, context);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Intent intent = new Intent();
            intent.setAction(UPDATE_USERS);
            LocalBroadcastManager.getInstance(RecentUsersService.this).sendBroadcast(intent);
        }
    }

    public RecentUsersService() {
        super("RecentUsersService");
    }

    public static void startActionUpdate(Context context) {
        Intent intent = new Intent(context, RecentUsersService.class);
        intent.setAction(UPDATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE.equals(action)) {
                handleActionUpdate();
            }
        }
    }

    private void handleActionUpdate() {
        /*for (int i = 0; i < 10; i++) {
            Log.d("TAG", "handleActionUpdate: sdasd");
        }*/

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("page", "1");
        new UpdateUsers(5, this).execute(queryParams);
    }
}
