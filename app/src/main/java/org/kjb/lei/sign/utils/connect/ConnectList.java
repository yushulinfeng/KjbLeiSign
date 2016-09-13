package org.kjb.lei.sign.utils.connect;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络参数封装类
 */
public class ConnectList {
    private Map<String, String> map = null;

    public ConnectList() {
        map = new HashMap<>();
    }

    public ConnectList put(String key, String value) {
        map.put(key, value == null ? "" : value);
        return this;
    }

    public ConnectList put(String type) {
        put(ServerURL.REQUEST_TYPE, type);
        return this;
    }

    public Map<String, String> getMap() {
        return map;
    }

}