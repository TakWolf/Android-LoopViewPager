package com.takwolf.android.loopviewpagerdemo.util;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public final class ResUtils {

    private ResUtils() {}

    @ColorInt
    public static int getThemeAttrColor(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
        try {
            return a.getColor(0, 0);
        } finally {
            a.recycle();
        }
    }

}
