package com.abhi.feedxmlparsing.net;

/**
 * Created by abhijeet on 1/2/18.
 */

public interface ServiceCallBack {

    int CALL_FEED_SERVICE=101;

    public void onResult(Object data,int caller);


    public void onError(String error,int caller);

    public void onRequestCancel(String error,int caller);
}
