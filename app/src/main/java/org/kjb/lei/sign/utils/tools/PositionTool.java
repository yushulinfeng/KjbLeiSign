package org.kjb.lei.sign.utils.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

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
        try {
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
            return new Point(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            return null;
        }
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

    public void saveReferPosition(Context context, String positionJson) {
        ShareTool.saveText(context, positionJson, "position");
    }

    public Point getReferPosition(Context context, String placeName) {
        String text = ShareTool.getText(context, "position");
        if (!StaticMethod.positionCheck(text)) {
            return new Point(36.666383, 117.134116);
        }
        return null;
    }

}
