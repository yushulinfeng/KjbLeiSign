package org.kjb.lei.sign.start;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
import org.kjb.lei.sign.utils.tools.PositionTool;
import org.kjb.lei.sign.utils.tools.UserTool;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnLongClick;

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
        getMessage();

    }

    private void getMessage() {
        name = getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            loginEtName.setText(name);
            loginCbTeacher.setChecked(true);
            loginEtPass.requestFocus();
        }
    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.login_btn_register:
                startActivity(new Intent(this, Register.class));
                finish();
                break;
        }
    }

    @OnLongClick({R.id.login_btn_login})
    public boolean onLongClick(View view) {
        //长按登录设置IP地址
        String ip = loginEtPass.getText().toString();
        if (TextUtils.isEmpty(ip) && loginEtPass.getHint().equals("密码")) {
            loginEtPass.setHint("IP地址");
            loginEtPass.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
            StaticMethod.showToast("请在密码框输入IP");
        } else {
            if (!TextUtils.isEmpty(ip) && ip.contains(".")
                    && loginEtPass.getHint().equals("IP地址")) {
                ServerURL.setIP(ip);
                StaticMethod.showToast("IP修改成功");
                loginEtPass.setText("");
            }
            loginEtPass.setHint("密码");
            loginEtPass.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        return true;
    }

    public void login() {
        //获取数据
        name = loginEtName.getText().toString();
        pass = loginEtPass.getText().toString();
        is_teacher = loginCbTeacher.isChecked();
        SignMain.is_teacher = true;
        //安全检查（最大长度已在xml中定义）
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
            StaticMethod.showToast("请填写登录信息");
            return;
        }
        if (name.length() != 12) {
            StaticMethod.showToast("请填写正确的账号");
            return;
        }
        loginBtnLogin.setEnabled(false);
        StaticMethod.showProgressDialog(this, "正在登录");
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
                    getReferPosition();
                    SignMain.is_teacher = is_teacher;
                    SignMain.real_name = is_teacher ? "教师" : "学生";
                    SignMain.class_table = new ArrayList<AnClass>();
                    if ("1".equals(response)) {//特殊情况，返回课表失败
                        ////跳转到主页，告诉他获取信息失败
                        StaticMethod.showToast("获取用户信息失败");
                    } else {
                        try {//解析用户信息
                            AnClassInfo info = new Gson().fromJson(response, AnClassInfo.class);
                            SignMain.real_name = info.getName();
                            SignMain.class_table = info.getTable();
                            //存储课程信息
                            InfoTool.saveClassInfo(Login.this, response);
                            //存储用户名与密码
                            UserTool.saveUserInfo(Login.this, name, pass, is_teacher);
                        } catch (Exception e) {
                            StaticMethod.showToast("获取用户信息失败");
                        }
                    }
                    startActivity(new Intent(Login.this, SignMain.class));
                    finish();
                }
            }
        });
    }

    //对于学生，要获取相对的签到参考位置，嗯。
    private void getReferPosition() {
        if (!SignMain.is_teacher)
            StaticMethod.POST(ServerURL.SIGN, new ConnectListener() {
                @Override
                public ConnectList setParam(ConnectList list) {
                    list.put(ServerURL.TYPE_POSITION);
                    return list;
                }

                @Override
                public void onResponse(String response) {
                    StaticMethod.showLog(response);//////
                    //缓存位置信息到本地
                    new PositionTool(Login.this).saveReferPosition(Login.this, response);
                }
            });
    }
}
