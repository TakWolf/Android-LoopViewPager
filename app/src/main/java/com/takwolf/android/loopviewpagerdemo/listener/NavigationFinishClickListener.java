package com.takwolf.android.loopviewpagerdemo.listener;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class NavigationFinishClickListener implements View.OnClickListener {

    private final Activity activity;

    public NavigationFinishClickListener(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        ActivityCompat.finishAfterTransition(activity);
    }

}
