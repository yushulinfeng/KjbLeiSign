package org.kjb.lei.sign.start;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by YuShuLinFeng on 2016/9/7.
 */
public class Register extends BaseActivity {

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
                break;
            case R.id.register_btn_login:
                startActivity(new Intent(this, Login.class));
                finish();
                break;
        }
    }
}
