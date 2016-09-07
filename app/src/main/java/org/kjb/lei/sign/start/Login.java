package org.kjb.lei.sign.start;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by YuShuLinFeng on 2016/9/7.
 */
public class Login extends BaseActivity {

    @Bind(R.id.login_et_name)
    EditText loginEtName;
    @Bind(R.id.login_et_pass)
    EditText loginEtPass;
    @Bind(R.id.login_cb_teacher)
    CheckBox loginCbTeacher;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.start_login;
    }

    @Override
    protected void afterCreate() {

    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                StaticMethod.showToast("登录");
                break;
            case R.id.login_btn_register:
                startActivity(new Intent(this, Register.class));
                finish();
                break;
        }
    }

}
