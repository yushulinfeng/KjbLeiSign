package org.kjb.lei.sign.start;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.base.BaseActivity;
import org.kjb.lei.sign.utils.tools.UserTool;

/**
 * Created by YuShuLinFeng on 2016/9/7.
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
        if (TextUtils.isEmpty(user[0])) {//没有登录历史
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }
        autoLogin();//有历史就自动登录
    }

    private void autoLogin() {

    }

}
