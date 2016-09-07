package org.kjb.lei.sign.utils.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ShareTool {

    public static void saveText(Context context, String save_path, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(
                save_path, Activity.MODE_PRIVATE).edit();
        prefs.putString("text", text);
        prefs.commit();
    }

    public static String getText(Context context, String save_path) {
        SharedPreferences mPref = context.getSharedPreferences(
                save_path, Activity.MODE_PRIVATE);
        String text = mPref.getString("text", null);
        return text;
    }

}
