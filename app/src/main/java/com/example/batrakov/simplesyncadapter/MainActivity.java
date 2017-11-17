package com.example.batrakov.simplesyncadapter;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
        ContentResolver resolver = getContentResolver();

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.GET_ACCOUNTS)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        mListView = findViewById(R.id.list);

        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
        Account account = null;

        for (Account a : accounts) {
            System.out.println(a.name);
            account = a;
        }

        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null,null);

        if (cursor != null) {
            for (String str :
                    cursor.getColumnNames()) {
                System.out.println(str);
            }
        } else {
            System.out.println("cursor is null");
        }

        String from[] = { "_id", "display_name" };
        int to[] = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, cursor, from, to, 0);

        mListView.setAdapter(adapter);

        if (account != null) {
            System.out.println(account.type);
            Bundle b = new Bundle();
            // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            ContentResolver.requestSync(account, ContactsContract.AUTHORITY, b);
        }
    }
}
