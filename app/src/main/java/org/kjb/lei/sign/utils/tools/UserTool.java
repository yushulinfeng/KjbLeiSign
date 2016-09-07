package org.kjb.lei.sign.utils.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by YuShuLinFeng on 2016/9/8.
 */
public class UserTool {
    private static final String USER_LOGIN_INFO = "user_info";

    /**
     * 获取用户名、密码
     */
    public static String[] getUserInfo(Context context) {
        SharedPreferences mPref = context.getSharedPreferences(
                USER_LOGIN_INFO, Activity.MODE_PRIVATE);
        String name = mPref.getString("name", "");
        String pass = mPref.getString("pass", "");
        return new String[]{name, pass};
    }

    /**
     * 存储用户名、密码
     */
    public static void saveUserInfo(Context context, String name, String pass) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(
                USER_LOGIN_INFO, Activity.MODE_PRIVATE).edit();
        prefs.putString("name", name);
        prefs.putString("pass", pass);
        prefs.commit();
    }

}
