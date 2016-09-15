package org.kjb.lei.sign.start;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.main.SignMain;
import org.kjb.lei.sign.model.AnClass;
import org.kjb.lei.sign.model.AnClassInfo;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseActivity;
import org.kjb.lei.sign.utils.connect.ConnectList;
import org.kjb.lei.sign.utils.connect.ConnectListener;
import org.kjb.lei.sign.utils.connect.ServerURL;
import org.kjb.lei.sign.utils.tools.InfoTool;
import org.kjb.lei.sign.utils.tools.TestTool;
import org.kjb.lei.sign.utils.tools.UserTool;

import java.util.ArrayList;

/**
 * 欢迎界面
 */
public class Welcome extends BaseActivity {
    private String[] user = null;

    @Override
    protected int getLayoutId() {
        return R.layout.start_welcome;
    }

    @Override
    protected void afterCreate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMain();
            }
        }, 1000);
    }

    private void gotoMain() {
        user = UserTool.getUserInfo(this);
        TestTool.initTestInfo(this);
        if (TextUtils.isEmpty(user[0])) {//没有登录历史
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }
        autoLogin();//有历史就自动登录
    }

    //自动登录
    private void autoLogin() {
        SignMain.is_teacher = user[2].equals("1");
        SignMain.real_name = SignMain.is_teacher ? "教师" : "学生";
        SignMain.class_table = new ArrayList<AnClass>();
        StaticMethod.POST(ServerURL.LOGIN, new ConnectListener() {
            @Override
            public ConnectList setParam(ConnectList list) {
                list.put(ServerURL.TYPE_LOGIN);
                list.put("name", user[0]);
                list.put("pass", user[1]);
                list.put("type", SignMain.is_teacher ? "1" : "0");
                list.put("auto", "1");
                return list;
            }

            @Override
            public void onResponse(String response) {
                StaticMethod.showLog(response);//////
                if ("1".equals(response)) {//登录成功
                    try {//解析用户信息
                        String info_str = InfoTool.getClassInfo(Welcome.this);
                        AnClassInfo info = new Gson().fromJson(info_str, AnClassInfo.class);
                        SignMain.real_name = info.getName();
                        SignMain.class_table = info.getTable();
                    } catch (Exception e) {
                        StaticMethod.showToast("获取用户信息失败");
                    }
                    startActivity(new Intent(Welcome.this, SignMain.class));
                    finish();
                } else {
                    StaticMethod.showToast("自动登录失败");
                    Intent intent = new Intent(Welcome.this, Login.class);
                    intent.putExtra("name", user[0]);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
