package org.kjb.lei.sign.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.kjb.lei.sign.start.Login;
import org.kjb.lei.sign.start.Register;
import org.kjb.lei.sign.start.Welcome;
import org.kjb.lei.sign.utils.tools.PositionTool;

/**
 * 测试界面
 */
public class SignTest extends Activity {
    private Context context;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        View view = initView();
        initButton();
        addDivider();
        addBtnCancel();
        setContentView(view);
    }

    private View initView() {
        layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);
        ScrollView scroll = new ScrollView(context);
        scroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        scroll.addView(layout);
        return scroll;
    }

    private Button addButton(String text, View.OnClickListener listener) {
        Button btn = new Button(context);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        btn.setText(text);
        btn.setOnClickListener(listener);
        layout.addView(btn);
        return btn;
    }

    private void addDivider() {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setText("--------------------");
        layout.addView(tv);
    }

    private Button addButton(String text, final Class<?> cls) {
        return addButton(text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(cls);
            }
        });
    }

    private void startNewActivity(Class<?> cls) {
        startActivity(new Intent(context, cls));
    }

    private void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private void addBtnCancel() {
        addButton("退出", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initButton() {
        addButton("TEST", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("TEST");
            }
        });
        addButton("定位", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PositionTool(context).getPosition();
            }
        });
        addButton("欢迎", Welcome.class);
        addButton("注册", Register.class);
        addButton("登录", Login.class);
        addButton("主页", SignMain.class);
    }

}