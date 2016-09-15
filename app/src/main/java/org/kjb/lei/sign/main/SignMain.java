package org.kjb.lei.sign.main;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.fragment.ClassFragment;
import org.kjb.lei.sign.fragment.SignFragment;
import org.kjb.lei.sign.model.AnClass;
import org.kjb.lei.sign.start.Login;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseActivity;
import org.kjb.lei.sign.utils.tools.TestTool;
import org.kjb.lei.sign.utils.tools.TimerTool;
import org.kjb.lei.sign.utils.tools.UserTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 主界面
 */
@SuppressWarnings({"deprecation", "ConstantConditions"})
public class SignMain extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private long last_back_time = 0;
    private ArrayList<Fragment> fragments;
    private int index = 0;
    //使用静态量便于操作（仅在Login/Welcome中初始化）
    public static boolean is_teacher = false;//是否是教师
    public static String real_name = "学生";//真实姓名
    public static List<AnClass> class_table = null;//课程信息

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    TextView headerTv;
    ImageView headerIv;

    @Override
    protected int getLayoutId() {
        return R.layout.sign_main;
    }

    @Override
    protected void afterCreate() {
        setSupportActionBar(toolbar);
        initMenu();
        initFragment();
        initTestExpand();
    }

    private void initMenu() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.nav_sign);
        headerTv = (TextView) navView.getHeaderView(0).findViewById(R.id.menu_header_tv);
        headerIv = (ImageView) navView.getHeaderView(0).findViewById(R.id.menu_header_iv);
        headerTv.setText(real_name);
        headerIv.setImageResource(is_teacher ? R.drawable.icon_teacher
                : R.drawable.icon_student);
    }

    private void initFragment() {
        SignFragment sign = new SignFragment();
        fragments = new ArrayList<>();
        fragments.add(sign);
        fragments.add(new ClassFragment());
        index = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragments.get(index), "")
                .commit();
        TimerTool.getInstance().setListener(sign);
        TimerTool.getInstance().startTimer(30 * 1000);//30秒刷新一次位置
    }

    private void initTestExpand() {
        headerIv.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(SignMain.this, SignTest.class));
                        return true;
                    }
                });
        if (TestTool.isTest()) {
            index = 1;
            switchContent(fragments.get(0), fragments.get(1));
            navView.setCheckedItem(R.id.nav_class);
        }
    }

    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in_200, R.anim.fade_out_200);
        if (from != to)         //是否是同一个
            transaction.hide(from);
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.add(R.id.main_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_sign) {
            if (index != 0) {
                index = 0;
                switchContent(fragments.get(1), fragments.get(0));
            }
        } else if (id == R.id.nav_class) {
            if (index != 1) {
                index = 1;
                switchContent(fragments.get(0), fragments.get(1));
            }
        } else if (id == R.id.nav_logout) {
            StaticMethod.showToast("注销成功");
            UserTool.saveUserInfo(this, "", "", false);
            startActivity(new Intent(this, Login.class));
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else if (System.currentTimeMillis() - last_back_time > 1000) {
                last_back_time = System.currentTimeMillis();
                StaticMethod.showToast("再按一次退出");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        TimerTool.getInstance().destroyTimer();
        super.onDestroy();
    }
}
