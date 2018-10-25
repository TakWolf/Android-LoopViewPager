# Android - LoopViewPager #

[![Build Status](https://travis-ci.org/TakWolf/Android-LoopViewPager.svg?branch=master)](https://travis-ci.org/TakWolf/Android-LoopViewPager)
[![Bintray](https://api.bintray.com/packages/takwolf/maven/Android-LoopViewPager/images/download.svg)](https://bintray.com/takwolf/maven/Android-LoopViewPager/_latestVersion)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/github/license/TakWolf/Android-LoopViewPager.svg)](http://www.apache.org/licenses/LICENSE-2.0)

An Android ViewPager extension allowing infinite scrolling.

Android 支持无限循环滚动的 ViewPager，特性如下：

- 继承自官方 `ViewPager`，可兼容大部分组件（无限模式下，边界部分的 UI 可能需要做一点适配）

- 新的 `RecycledPagerAdapter`，类似于 `RecyclerView.Adapter` 的实现，支持数据动态变化，支持子布局回收重用，支持多类型子布局

- 无限循环功能通过有限的子布局 + `OnPageChangeListener` 实现，而非通过让子布局数量逻辑无限大

- 布局位置自动修正，业务端无需特殊处理

- 支持子布局按方向滚动（部分场景）

- 支持 `ViewPager` 内边距和子布局外边距

该组件可以用于实现大部分应用轮播图的需求场景，也可以用于实现横向变长元素 `Pager` 视图（更推荐使用 `RecyclerView` + `PagerSnapHelper` 实现）

注意，该组件仅实现无限循环和回收布局功能，并不包含 `TabLayout`、指示器控件、轮播定时、纵向滚动功能。

## Usage ##

### Gradle ###

``` gradle
implementation 'com.takwolf.android:loop-viewpager:0.0.1'
implementation 'com.android.support:support-core-ui:28.0.0'
```

### Layout ###

``` xml
<com.takwolf.android.loopviewpager.LoopViewPager
    android:id="@+id/view_pager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:lvp_looping="true" />
```

### Java ###

请使用 `BannerPagerAdapter` 而非默认的 `PagerAdapter`，用法和 `RecyclerView.Adapter` 类似

``` java
public class ExamplePagerAdapter extends LoopViewPager.RecycledPagerAdapter<ExamplePagerAdapter.ViewHolder> {

    @Override
    public int getItemCount() {
        // TODO
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO
    }

    class ViewHolder extends LoopViewPager.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        // TODO

    }

}
```

设置 `LoopViewPager`

```java
LoopViewPager viewPager = (LoopViewPager) findViewById(R.id.view_pager);
ExamplePagerAdapter adapter = new ExamplePagerAdapter();
viewPager.setDataAdapter(adapter);
```

## Author ##

TakWolf

[takwolf@foxmail.com](mailto:takwolf@foxmail.com)

[http://takwolf.com](http://takwolf.com)

## License ##

```
Copyright 2018 TakWolf

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
