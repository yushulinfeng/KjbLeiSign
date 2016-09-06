package org.kjb.lei.sign.main;

import android.app.Application;

/**
 * Created by YuShuLinFeng on 2016/9/6.
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

}
