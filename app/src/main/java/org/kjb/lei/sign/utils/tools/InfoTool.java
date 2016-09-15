package org.kjb.lei.sign.utils.tools;

import android.content.Context;

/**
 * 课程等信息存储
 */
public class InfoTool {
    private static final String USER_CLASS_INFO = "user_class_info";

    public static void saveClassInfo(Context context, String info) {
        ShareTool.saveText(context, USER_CLASS_INFO, info);
    }

    public static String getClassInfo(Context context) {
        return ShareTool.getText(context, USER_CLASS_INFO);
    }
}
