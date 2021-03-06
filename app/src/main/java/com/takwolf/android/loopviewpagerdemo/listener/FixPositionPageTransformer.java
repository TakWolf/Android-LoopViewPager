package com.takwolf.android.loopviewpagerdemo.listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public final class FixPositionPageTransformer implements ViewPager.PageTransformer {

    @NonNull
    private final ViewPager.PageTransformer pageTransformer;

    public FixPositionPageTransformer(@NonNull ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
    }

    @NonNull
    public ViewPager.PageTransformer getPageTransformer() {
        return pageTransformer;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (page.getParent() instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) page.getParent();
            float clientWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
            position = (page.getLeft() - viewPager.getScrollX() - viewPager.getPaddingLeft()) / clientWidth;
        }
        pageTransformer.transformPage(page, position);
    }

}
