package com.desafiolatam.desafioface.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.views.login.LoginActivity;
import com.desafiolatam.desafioface.views.main.MainActivity;
import com.orm.SugarApp;

/**
 * Created by Pedro on 03-08-2017.
 */

public class DesafioApp extends SugarApp {

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    public static final String EXPIRED_SESSION = "EXPIRED_SESSION";

    @Override
    public void onCreate() {
        super.onCreate();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                CurrentUser.deleteAll(CurrentUser.class);
                Toast.makeText(context, "Sesión expirada", Toast.LENGTH_SHORT).show();
                Intent logOutIntent = new Intent(context, LoginActivity.class);
                logOutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logOutIntent);
            }
        };

        intentFilter = new IntentFilter();
        intentFilter.addAction(EXPIRED_SESSION);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
