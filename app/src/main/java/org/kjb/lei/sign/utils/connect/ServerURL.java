package org.kjb.lei.sign.utils.connect;

/**
 * 网址配置类
 */
public class ServerURL {
    // 服务器IP地址
    private static String SERVER_IP = "120.27.98.95"; //120.27.98.95

    //注册与登录地址
    public static final String LOGIN = "UserAction";
    //签到与查看地址
    public static final String SIGN = "SignAction";

    //类型常量
    public static final String REQUEST_TYPE = "REQUEST_TYPE";
    public static final String TYPE_REGISTER = "REGISTER";
    public static final String TYPE_LOGIN = "LOGIN";
    public static final String TYPE_SIGN = "SIGN";
    public static final String TYPE_WATCH = "WATCH";
    public static final String TYPE_POSITION = "POSITION";

    //获取请求地址
    public static String getUrl(String url) {
        return "http://" + SERVER_IP + ":8080/kjb_sign_lei/" + url;
    }

    // 设定IP地址
    public static void setIP(String ip) {
        SERVER_IP = ip;
    }

}
