package org.kjb.lei.sign.main;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import org.kjb.lei.sign.R;
import org.kjb.lei.sign.utils.all.StaticMethod;
import org.kjb.lei.sign.utils.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by YuShuLinFeng on 2016/9/6.
 */
@SuppressWarnings({"deprecation", "ConstantConditions"})
public class SignMain extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private long last_back_time = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected int getLayoutId() {
        return R.layout.sign_main;
    }

    @Override
    protected void afterCreate() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.nav_sign);
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

        } else if (id == R.id.nav_class) {

        } else if (id == R.id.nav_tell) {

        } else if (id == R.id.nav_logout) {

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

}
