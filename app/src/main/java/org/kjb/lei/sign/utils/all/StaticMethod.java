package org.kjb.lei.sign.utils.all;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.kjb.lei.sign.main.SignApplication;
import org.kjb.lei.sign.utils.connect.ConnectEasy;
import org.kjb.lei.sign.utils.connect.ConnectListener;
import org.kjb.lei.sign.utils.connect.ServerURL;

/**
 * 方法整合工具类
 */
public class StaticMethod {
    private static Handler handler = new Handler();
    private static float dp_px_scare = 0;//dp与px比例
    private static ProgressDialog dialog = null;

    /**
     * 显示提示信息
     */
    public static void showToast(String text) {
        if (text != null)
            Toast.makeText(SignApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长提示信息
     */
    public static void showLongToast(String text) {
        if (text != null)
            Toast.makeText(SignApplication.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * 在非UI线程，显示提示信息
     */
    public static void showThreadToast(final String text) {
        handler.post(new Runnable() {// 发送到主线程进行提示
            public void run() {
                showToast(text);
            }
        });
    }

    /**
     * 显示提示信息
     */
    public static void showLog(Object text) {
        Log.e("EEEEE", "" + text);
    }

    /**
     * 获取handler，用于在支线程向主线程调用
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取列表的项目View
     */
    public static View getItemView(Context context, int layout) {
        return LayoutInflater.from(context).inflate(layout, null);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dipTopx(Context context, float dpValue) {
        if (dp_px_scare == 0)
            dp_px_scare = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * dp_px_scare + 0.5f);
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(final Context context) {
        boolean netStatus = false;
        ConnectivityManager connectManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            netStatus = networkInfo.isAvailable();
        }
        return netStatus;
    }

    /**
     * 显示键盘
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏键盘(基于Activity)
     */
    public static void hideKeyboardEasy(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    /**
     * 位置检查，确保返回正确的默认位置
     */
    public static boolean positionCheck(String position) {
        if (position == null)
            return false;
        else if (position.equals(""))
            return false;
        return false;
    }

    /**
     * 显示进度对话框
     */
    public static void showProgressDialog(Context context, String title) {
        try {//传入错误的context可能无法显示
            dialog = ProgressDialog.show(context, title, "请稍候……");
        } catch (Exception e) {
        }
    }

    /**
     * 隐藏进度对话框
     */
    public static void hideProgressDialog() {
        if (dialog != null && dialog.isShowing())
            try {//防止窗口句柄泄露报错
                dialog.hide();
            } catch (Exception e) {
            }
        dialog = null;//需要这样，否则跨Activity会出错
    }

    /**
     * 发起网络请求
     */
    public static void POST(String url, final ConnectListener listener) {
        if (!TextUtils.isEmpty(url)) url = ServerURL.getUrl(url);//封装URL
        ConnectEasy.POST(url, listener);
    }

}
