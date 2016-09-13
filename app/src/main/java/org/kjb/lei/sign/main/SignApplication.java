package org.kjb.lei.sign.main;

import android.app.Application;

/**
 * 应用层
 */
public class SignApplication extends Application {
    private static SignApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static SignApplication getInstance() {
        return instance;
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
//        SDKInitializer.initialize(this);
    }
}
