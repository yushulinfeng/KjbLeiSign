package org.kjb.lei.sign.utils.connect;

/**
 * 网址配置类
 */
public class ServerURL {
    // 服务器IP地址
    private static final String SERVER_IP = "http://192.168.123.1:8080/kjb_sign_lei/";
    //注册与登录地址
    public static final String LOGIN = SERVER_IP + "UserAction";
    //签到与查看地址
    public static final String SIGN = SERVER_IP + "SignAction";

    //类型常量
    public static final String REQUEST_TYPE = "REQUEST_TYPE";
    public static final String TYPE_REGISTER = "REGISTER";
    public static final String TYPE_LOGIN = "LOGIN";
    public static final String TYPE_SIGN = "SIGN";
    public static final String TYPE_WATCH = "WATCH";
}
