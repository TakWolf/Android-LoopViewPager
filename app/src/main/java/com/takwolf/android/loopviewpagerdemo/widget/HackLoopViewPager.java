package com.takwolf.android.loopviewpagerdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.takwolf.android.loopviewpager.LoopViewPager;

public class HackLoopViewPager extends LoopViewPager {

    public HackLoopViewPager(@NonNull Context context) {
        super(context);
    }

    public HackLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onAttachedToWindow() {}

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDetachedFromWindow() {}

}
