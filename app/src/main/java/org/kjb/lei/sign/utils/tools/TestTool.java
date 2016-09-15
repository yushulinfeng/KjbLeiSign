package org.kjb.lei.sign.utils.tools;

import android.content.Context;

/**
 * 测试工具类
 */
public class TestTool {
    private static boolean isTest = false;
    private static final String TEST_INFO = "test_info";

    public static void saveTestInfo(Context context, boolean test) {
        ShareTool.saveText(context, TEST_INFO, test ? "1" : "0");
        isTest = test;
    }

    public static boolean initTestInfo(Context context) {
        String test = ShareTool.getText(context, TEST_INFO);
        isTest = "1".equals(test);//默认false
        return isTest;
    }

    public static boolean isTest() {
        return isTest;
    }
}
