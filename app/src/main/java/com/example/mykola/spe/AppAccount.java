package com.example.mykola.spe;

import android.accounts.Account;

import javax.annotation.Nullable;

/**
 * Created by mykola on 02.09.17.
 */

public class AppAccount {

    private static AppAccount sInstance;

    public static AppAccount getInstance() {
        if (sInstance == null) {
            sInstance = new AppAccount();
        }
        return sInstance;
    }

    public Account account;
}
