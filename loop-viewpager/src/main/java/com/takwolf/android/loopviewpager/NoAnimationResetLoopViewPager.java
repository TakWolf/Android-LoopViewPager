package com.takwolf.android.loopviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoAnimationResetLoopViewPager extends LoopViewPager {

    public NoAnimationResetLoopViewPager(@NonNull Context context) {
        super(context);
    }

    public NoAnimationResetLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onAttachedToWindow() {}

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDetachedFromWindow() {}

}
