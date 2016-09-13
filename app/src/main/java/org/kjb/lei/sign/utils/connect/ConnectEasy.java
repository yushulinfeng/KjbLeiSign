package org.kjb.lei.sign.utils.connect;

import android.text.TextUtils;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

/**
 * 简化网络连接类
 */
public class ConnectEasy {
    private static RequestQueue requestQueue = null;

    public static void POST(String url, final ConnectListener listener) {
        if (TextUtils.isEmpty(url) || listener == null)
            return;
        if (requestQueue == null)
            requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        ConnectList list = listener.setParam(new ConnectList());
        if (list == null)
            list = new ConnectList();
        if (list.getMap().size() > 0)
            request.add(list.getMap());
        OnResponseListener responseListener = new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {//可以在此处showDialog
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response != null)
                    listener.onResponse(response.get());
                else
                    listener.onResponse(null);
            }

            @Override
            public void onFailed(int what, String url, Object tag,
                                 Exception exception, int responseCode, long networkMillis) {
                listener.onResponse(null);
            }

            @Override
            public void onFinish(int what) {//可以再此处hideDialog
            }
        };
        requestQueue.add(0, request, responseListener);
    }

}