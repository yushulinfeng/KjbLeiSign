package org.kjb.lei.sign.utils.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import org.kjb.lei.sign.utils.all.StaticMethod;

import java.util.List;

/**
 * 位置获取工具类
 */
public class PositionTool {
    private LocationManager manager;

    public PositionTool(Context context) {
        this.manager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 获取当前位置
     */
    public Point getPosition() {
        //获取所有可用的位置提供器
        List<String> providers = manager.getProviders(true);
        String provider = null;
        if (providers.contains(manager.GPS_PROVIDER)) {// GPS位置
            provider = manager.GPS_PROVIDER;
        } else if (providers.contains(manager.NETWORK_PROVIDER)) {//网络位置
            provider = manager.NETWORK_PROVIDER;
        } else {//获取位置失败
            return null;
        }
        @SuppressWarnings("MissingPermission")
        Location location = manager.getLastKnownLocation(provider);
        Log.e("EEEEE", location.getLatitude() + "-" + location.getLongitude());
        StaticMethod.showLongToast(location.getLatitude() + "\n" + location.getLongitude());
        //+ 0.0004// + 0.006
        return new Point(location.getLatitude(), location.getLongitude());
    }

    /**
     * 位置点
     */
    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}
