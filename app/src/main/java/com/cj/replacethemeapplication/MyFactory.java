package com.cj.replacethemeapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cj.replacethemeapplication.util.LoadSkinManager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class MyFactory implements LayoutInflater.Factory2 {
    public static final String TAG = "MyFactory";
    String[] packageSuff = {
            "android.view.",
            "android.widget.",
            "android.webkit."
    };
    //收集控件
    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.d(TAG, "onCreateView111: name--"+name);
        View view = null;
        //带包名
        if (name.contains(".")){
            view = onCreateView(name, context, attrs);
        }else {
            for (String s:packageSuff){
               String mName = s+name;
                view = onCreateView(mName, context, attrs);
                if (view !=null){
                    break;
                }
            }
        }
        parseView(view,name,attrs);
        return view;
    }

    private List<SkinView> mList = new ArrayList<>();
    public void apply(){
        for (SkinView view:mList){
            view.apply();
        }
    }
    class SkinView{
        public View view;
        public List<SkinItem> list;

        public SkinView(View view, List<SkinItem> list) {
            this.view = view;
            this.list = list;
        }

        public void apply(){
            for (SkinItem item:list){
                if (item.attributeName.equals("textColor")){
                    if (item.typeName.equals("color")){
                        int color = LoadSkinManager.getInstance().getColor(item.resId);
                        ((TextView)view).setTextColor(color);
                    }
                }
            }
        }
    }

    class SkinItem{
        public String attributeName;
        public String name;
        public String typeName;
        public int resId;
    }
    private void parseView(View view, String name, AttributeSet attrs) {
        List<SkinItem> list = new ArrayList<>();
        for (int i = 0;i<attrs.getAttributeCount();i++){
            String attributeName = attrs.getAttributeName(i);
            if ("textColor".equals(attributeName)){
                SkinItem skinItem = new SkinItem();
                skinItem.attributeName = attributeName;
                String attributeValue = attrs.getAttributeValue(i);
                int resId = Integer.parseInt(attributeValue.substring(1));
                skinItem.resId = resId;
                skinItem.typeName = view.getResources().getResourceTypeName(resId);
                skinItem.name = view.getResources().getResourceEntryName(resId);
                list.add(skinItem);
            }
        }
        if (list.size()>0){
            SkinView skinView = new SkinView(view,list);
            mList.add(skinView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.d(TAG, "onCreateView222: name--"+name);
        View view = null;
        try {
            Class aClass = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = aClass.getConstructor(Context.class, AttributeSet.class);
            view = constructor.newInstance(context,attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
