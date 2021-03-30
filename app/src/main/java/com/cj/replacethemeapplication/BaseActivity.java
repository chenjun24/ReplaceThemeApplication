package com.cj.replacethemeapplication;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cj.replacethemeapplication.util.LoadSkinManager;

import java.lang.reflect.Field;

class BaseActivity extends AppCompatActivity {
    private MyFactory factory2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadSkinManager instance = LoadSkinManager.getInstance();
        instance.setContext(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        factory2 = new MyFactory();
        try {
            Field mFactory2 = LayoutInflater.class.getDeclaredField("mFactory2");
            mFactory2.setAccessible(true);
            mFactory2.set(layoutInflater,factory2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void apply(){
        factory2.apply();
    }
}
