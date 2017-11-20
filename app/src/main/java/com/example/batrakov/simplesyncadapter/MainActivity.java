package com.example.batrakov.simplesyncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountManager manager = AccountManager.get(this);
        mAccount = manager.getAccountsByType("com.google")[0];
        Bundle bundle = new Bundle();
        ContentResolver.requestSync(mAccount, "com.example.batrakov.simplesyncadapter.contentProvider", bundle);
    }

}
