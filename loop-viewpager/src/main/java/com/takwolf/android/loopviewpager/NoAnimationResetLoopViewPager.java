package com.takwolf.android.loopviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

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
