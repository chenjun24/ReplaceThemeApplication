package com.cj.replacethemeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cj.replacethemeapplication.util.LoadSkinManager;

public class SecondActivity extends BaseActivity {
    public static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void replaceSkin(View view) {
        Log.d(TAG, "replaceSkin: 1111");
        LoadSkinManager.getInstance().loadApk();
        apply();
    }
}