package org.kjb.lei.sign.utils.connect;

/**
 * 网络监听回调类
 */
public interface ConnectListener {
    /**
     * 网络请求的参数
     *
     * @param list 默认的参数列表（空表，直接put然后返回即可）
     * @return 添加参数后的参数列表
     */
    public ConnectList setParam(ConnectList list);

    /**
     * 网络执行完毕后自动回调
     *
     * @param response 服务器返回的数据，错误将返回null
     */
    public void onResponse(String response);
}