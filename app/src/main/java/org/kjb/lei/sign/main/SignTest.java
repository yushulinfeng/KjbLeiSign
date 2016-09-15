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

import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.tools.TestTool;

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
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setText("软件测试");
        layout.addView(tv);
        addDivider();
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
        addButton("返回", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initButton() {
        addButton("课程表模式开关", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = !TestTool.isTest();
                TestTool.saveTestInfo(SignTest.this, state);
                if (state) {//开启了模式
                    StaticMethod.showToast("课表模式--已开启");
                } else {
                    StaticMethod.showToast("课表模式--已关闭");
                }
                Intent intent = new Intent(SignTest.this, SignMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}