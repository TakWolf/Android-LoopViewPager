package com.takwolf.android.loopviewpagerdemo.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.takwolf.android.loopviewpagerdemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_normal_demo)
    void onBtnNormalDemoClick() {
        startActivity(new Intent(this, NormalDemoActivity.class));
    }

    @OnClick(R.id.btn_list_header)
    void onBtnListHeaderClick() {
        startActivity(new Intent(this, ListHeaderActivity.class));
    }

}
