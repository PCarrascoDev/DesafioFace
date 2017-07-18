package com.desafiolatam.desafioface.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;


public class RecentUsersService extends IntentService {
    private static final String UPDATE = "com.desafiolatam.desafioface.services.action.FOO";

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
        for (int i = 0; i < 10; i++) {
            Log.d("TAG", "handleActionUpdate: sdasd");
        }
    }
}
