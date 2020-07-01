package com.vb.redditposts.ui.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivityPresenter {

    public void requestsForInternetPermission(int requestCode, Context context, Activity activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.INTERNET}
                    , requestCode);
        }
    }
}
