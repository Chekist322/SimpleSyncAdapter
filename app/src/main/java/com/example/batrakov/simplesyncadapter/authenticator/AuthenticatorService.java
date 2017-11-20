package com.example.batrakov.simplesyncadapter.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by batrakov on 20.11.17.
 */

public class AuthenticatorService extends Service {

    private static StubAuthenticator sStubAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        sStubAuthenticator = new StubAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sStubAuthenticator.getIBinder();
    }
}
