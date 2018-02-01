package com.abhi.feedxmlparsing.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.abhi.feedxmlparsing.model.Entry;
import com.abhi.feedxmlparsing.utilitis.RssXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Created by abhijeet on 1/2/18.
 */

public class DownloadNetworkTask extends AsyncTask<String,Integer,Boolean> {

    ProgressDialog mProgressDialog;
    private Context mContext;
    List<Entry> mEntries = null;
    ServiceCallBack mRequestCallBack;
    int mCaller;

    public DownloadNetworkTask(Context context,ServiceCallBack requestCallBack,int caller){
        mContext=context;
        mRequestCallBack=requestCallBack;
        mCaller=caller;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog=new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Please wait...");
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        try {
            URL url=new URL(urls[0]);
            HttpURLConnection httpConnection= (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(15000);//milliseconds
            httpConnection.setReadTimeout(10000);
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.connect();
            InputStream is=httpConnection.getInputStream();
            mEntries=new RssXmlParser().parse(is);
            is.close();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mProgressDialog.cancel();
        if(result){
            mRequestCallBack.onResult(mEntries,mCaller);
        }else{
            mRequestCallBack.onError("faild: ",mCaller);
        }
    }
}
