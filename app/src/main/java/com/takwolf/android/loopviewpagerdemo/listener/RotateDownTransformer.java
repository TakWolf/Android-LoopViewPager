package com.takwolf.android.loopviewpagerdemo.listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class RotateDownTransformer implements ViewPager.PageTransformer {

    private static final float ROT_MOD = -15f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float width = page.getWidth();
        float height = page.getHeight();
        float rotation = ROT_MOD * position * -1.25f;

        page.setPivotX(width * 0.5f);
        page.setPivotY(height);
        page.setRotation(rotation);
    }

}
