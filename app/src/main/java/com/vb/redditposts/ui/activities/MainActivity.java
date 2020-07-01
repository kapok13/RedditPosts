package com.vb.redditposts.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vb.redditposts.R;
import com.vb.redditposts.ui.presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity {

    MainActivityPresenter mMainActiityPresenter;

    private static int INTERNET_REQ_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActiityPresenter = new MainActivityPresenter();
        mMainActiityPresenter.requestsForInternetPermission(1, this, this);
    }
}
