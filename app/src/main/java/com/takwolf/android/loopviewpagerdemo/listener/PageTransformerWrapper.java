package com.takwolf.android.loopviewpagerdemo.listener;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public final class PageTransformerWrapper implements ViewPager.PageTransformer {

    @NonNull
    private final ViewPager.PageTransformer pageTransformer;

    public PageTransformerWrapper(@NonNull ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
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
