package org.kjb.lei.sign.main;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * 应用层
 */
public class SignApplication extends Application {
    private static SignApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        NoHttp.initialize(this);
    }

    public static SignApplication getInstance() {
        return instance;
    }

}
