package com.cj.replacethemeapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

public class LoadSkinManager {
    public static final String TAG = "LoadSkinManager";
    private static final String PATH = "/sdcard/skin.apk";
    private Context context;
    private String packageName;
    private Resources resources;
    private LoadSkinManager(){}

    private static LoadSkinManager loadSkinManager;
    public static LoadSkinManager getInstance(){
        if (loadSkinManager == null){
            synchronized (LoadSkinManager.class){
                if (loadSkinManager == null){
                    loadSkinManager = new LoadSkinManager();
                }
            }
        }
        return loadSkinManager;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void loadApk(){
        Log.d(TAG, "loadApk: ");
        PackageManager packageManager = context.getPackageManager();
        File file = new File(PATH);
        Log.d(TAG, "loadApk: ---"+file.exists()+"1111---"+file.getAbsolutePath());
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        if (packageArchiveInfo == null){
            Log.d(TAG, "loadApk: packageArchiveInfo--"+packageArchiveInfo);
            return;
        }
        packageName = packageArchiveInfo.packageName;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, PATH);
            resources = new Resources(assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //需要替换的资源id
    public int getColor(int resId){
        if (resources == null){
            return resId;
        }
        //@color/textColor
        String resourceTypeName = context.getResources().getResourceTypeName(resId);//color
        String resourceEntryName = context.getResources().getResourceEntryName(resId);//textColor
        int identifier = resources.getIdentifier(resourceEntryName, resourceTypeName, packageName);
        if (identifier == 0){
            return resId;
        }
        return identifier;
    }
}
