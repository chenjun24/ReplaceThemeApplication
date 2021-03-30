package com.cj.replacethemeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
       // apply();
    }
}