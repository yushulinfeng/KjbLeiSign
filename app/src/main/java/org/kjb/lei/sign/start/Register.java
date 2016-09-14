package org.kjb.lei.sign.start;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseActivity;
import org.kjb.lei.sign.utils.connect.ConnectList;
import org.kjb.lei.sign.utils.connect.ConnectListener;
import org.kjb.lei.sign.utils.connect.ServerURL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 教师注册界面
 */
public class Register extends BaseActivity {
    private String name, pass, real;

    @Bind(R.id.register_et_name)
    EditText registerEtName;
    @Bind(R.id.register_et_pass1)
    EditText registerEtPass1;
    @Bind(R.id.register_et_pass2)
    EditText registerEtPass2;
    @Bind(R.id.register_et_realname)
    EditText registerEtRealname;
    @Bind(R.id.register_btn_register)
    Button registerBtnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.start_register;
    }

    @Override
    protected void afterCreate() {

    }

    @OnClick({R.id.register_btn_register, R.id.register_btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_register:
                register();
                break;
            case R.id.register_btn_login:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
        }
    }

    private void register() {
        //获取数据
        name = registerEtName.getText().toString();
        pass = registerEtPass1.getText().toString();
        real = registerEtRealname.getText().toString();
        String pass2 = registerEtPass2.getText().toString();
        //安全检查（最大长度已在xml中定义）
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(real)) {
            StaticMethod.showToast("请填写注册信息");
            return;
        }
        if (name.length() != 12) {
            StaticMethod.showToast("请填写正确的工号");
            return;
        }
        if (pass.length() < 6) {
            StaticMethod.showToast("密码至少4位");
            return;
        }
        if (!pass.equals(pass2)) {
            StaticMethod.showToast("两次填写的密码不一致");
            return;
        }
        if (!isSafe(pass, real, true)) {
            return;
        }
        registerBtnRegister.setEnabled(false);
        StaticMethod.showProgressDialog(this, "正在注册……");
        //连接后台
        StaticMethod.POST(ServerURL.LOGIN, new ConnectListener() {
            @Override
            public ConnectList setParam(ConnectList list) {
                list.put(ServerURL.TYPE_REGISTER);
                list.put("name", name);
                list.put("pass", pass);
                list.put("real", real);
                return list;
            }

            @Override
            public void onResponse(String response) {
                StaticMethod.showLog(response);//////
                registerBtnRegister.setEnabled(true);
                StaticMethod.hideProgressDialog();
                if ("1".equals(response)) {
                    StaticMethod.showToast("注册成功");
                    Intent intent = new Intent(Register.this, Login.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    finish();
                } else if ("-1".equals(response)) {
                    StaticMethod.showToast("该用户已注册");
                } else if ("-3".equals(response)) {
                    StaticMethod.showToast("工号与姓名匹配失败");
                } else {
                    StaticMethod.showToast("注册失败");
                }
            }
        });
    }

    public static boolean isSafe(String pass, String real, boolean showTip) {
        Pattern pat = Pattern.compile("\\W");
        Matcher mat = pat.matcher(pass);
        if (mat.find()) {
            if (showTip)
                StaticMethod.showToast("密码不能包含非法字符");
            return false;
        }
        if (real == null) return true;
        pat = Pattern.compile("^[\\u4E00-\\u9FA5]+$");
        mat = pat.matcher(real);
        if (!mat.find()) {
            if (showTip)
                StaticMethod.showToast("真实姓名不能包含非法字符");
            return false;
        }
        return true;
    }


}
