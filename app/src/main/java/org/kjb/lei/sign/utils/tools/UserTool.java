package org.kjb.lei.sign.utils.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户存储工具类
 */
public class UserTool {
    private static final String USER_LOGIN_INFO = "user_login_info";

    /**
     * 获取用户名、密码
     */
    public static String[] getUserInfo(Context context) {
        SharedPreferences mPref = context.getSharedPreferences(
                USER_LOGIN_INFO, Activity.MODE_PRIVATE);
        String name = mPref.getString("name", "");
        String pass = mPref.getString("pass", "");
        String tea = mPref.getString("teacher", "0");//0学生，1教师
        return new String[]{name, pass, tea};
    }

    /**
     * 存储用户名、密码
     */
    public static void saveUserInfo(Context context, String name,
                                    String pass, boolean is_teacher) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(
                USER_LOGIN_INFO, Activity.MODE_PRIVATE).edit();
        prefs.putString("name", name);
        prefs.putString("pass", pass);
        prefs.putBoolean("teacher", is_teacher);
        prefs.commit();
    }

}
