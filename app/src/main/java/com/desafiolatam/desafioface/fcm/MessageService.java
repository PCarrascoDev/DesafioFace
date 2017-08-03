package com.desafiolatam.desafioface.fcm;

import com.desafiolatam.desafioface.notifications.FavoriteNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Pedro on 27-07-2017.
 */

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (!remoteMessage.getData().isEmpty())
        {
            String data = remoteMessage.getData().get("email");

            if (data != null)
            {
                FavoriteNotification.notify(this, data);
            }
        }
    }
}
