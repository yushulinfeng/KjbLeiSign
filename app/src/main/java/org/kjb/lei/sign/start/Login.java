package org.kjb.lei.sign.start;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseActivity;
import org.kjb.lei.sign.utils.connect.ConnectList;
import org.kjb.lei.sign.utils.connect.ConnectListener;
import org.kjb.lei.sign.utils.connect.ServerURL;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class Login extends BaseActivity {
    private String name, pass;
    private boolean is_teacher;

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

    private void getMessage() {
        name = getIntent().getStringExtra("name");
        if (name == null) name = "";
        loginEtName.setText(name);
    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void onButtonClick(View view) {
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

    public void login() {
        //获取数据
        name = loginEtName.getText().toString();
        pass = loginEtPass.getText().toString();
        is_teacher = loginCbTeacher.isChecked();
        //安全检查（最大长度已在xml中定义）
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
            StaticMethod.showToast("请填写登录信息");
            return;
        }
        loginBtnLogin.setEnabled(false);
        StaticMethod.showProgressDialog("正在登录……");
        if (!Register.isSafe(pass, null, false)) {
            StaticMethod.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StaticMethod.showToast("用户名或密码错误");
                    loginBtnLogin.setEnabled(true);
                    StaticMethod.hideProgressDialog();
                }
            }, 2000);
        }
        //连接网络
        StaticMethod.POST(ServerURL.LOGIN, new ConnectListener() {
            @Override
            public ConnectList setParam(ConnectList list) {
                list.put(ServerURL.TYPE_LOGIN);
                list.put("name", name);
                list.put("pass", pass);
                list.put("type", is_teacher ? "1" : "0");
                list.put("auto", "0");
                return list;
            }

            @Override
            public void onResponse(String response) {
                StaticMethod.showLog(response);//////
                loginBtnLogin.setEnabled(true);
                StaticMethod.hideProgressDialog();
                if ("-1".equals(response)) {
                    StaticMethod.showToast("用户名或密码错误");
                } else if (response == null || "-2".equals(response)) {
                    StaticMethod.showToast("登录失败");
                } else {
                    if ("1".equals(response)) {//特殊情况，返回课表失败
                        /////////////////////////////////////
                        ////跳转到主页，告诉他获取信息失败吧
                        return;
                    }
                    /////////////////////////////
                    //解析json课程信息，建议使用GSON
                    StaticMethod.showToast("SUCC");
                }
            }
        });
    }

}
