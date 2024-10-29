package com.example.mealmonkey.UI.utils;

import android.content.Context;
import com.techiness.progressdialoglibrary.ProgressDialog;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Utils {

    @Inject
    public Utils(){

    }

    public static ProgressDialog progressDialog;

    public static void showProgressDialog(Context content) {
        progressDialog = new ProgressDialog(content);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        progressDialog.dismiss();
    }
}
