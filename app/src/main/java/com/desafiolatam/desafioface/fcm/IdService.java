package com.desafiolatam.desafioface.fcm;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Pedro on 27-07-2017.
 */

public class IdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
