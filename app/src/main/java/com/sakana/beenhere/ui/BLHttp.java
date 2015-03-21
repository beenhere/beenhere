package com.sakana.beenhere.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caesare on 9/23/13.
 */
public class BLHttp extends AsyncTask<String, Void, String> {

     ProgressDialog pDialog;

Context context;
    public static String apiResult;
    /** Added Cookies - March 12, 2014**/
    public static List<Cookie> cookies;
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    List<NameValuePair> headerValuePairs = new ArrayList<NameValuePair>(2);
    private Runnable listener;
    public String result = "";
    private String cookie = "";

    public static boolean unreachable = false;

    public BLHttp(Runnable listener){
        this.listener = listener;
    }

    public BLHttp(Runnable listener,Context context){
        this.listener = listener;
        this.context=context;
    }


    public void addValue(String key, String value){
        nameValuePairs.add(new BasicNameValuePair(key, value));
    }

    /** Added Property headers **/
    public void addHeader(String key, String value){
        headerValuePairs.add(new BasicNameValuePair(key, value));
    }

    public void addCookie(String value){
        cookie = value;
    }

    @Override
    protected void onPreExecute() {

        if (context != null) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }
    protected String doInBackground(String... urls) {

        for (String url : urls) {

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            HttpPost httpPost = new HttpPost(url);

            DefaultHttpClient client = new DefaultHttpClient();

            final HttpParams httpParameters = client.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            client.setParams(httpParameters);
            System.out.println(url);
            try {
                System.out.println(nameValuePairs);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
                if(!cookie.isEmpty()) {
                    httpPost.addHeader("Cookie", cookie);
                }
                // Execute HTTP Post Request
                HttpResponse response =  client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                //20140312
                cookies = client.getCookieStore().getCookies();
                result = EntityUtils.toString(entity);
            } catch (ConnectTimeoutException e) {
                unreachable = true;
                result = "System not available";
            } catch (Exception e) {
                unreachable = true;
                result = "URL not available";
            }
        }
        return result;

    }

    protected void onPostExecute(String result) {
       if(pDialog!=null)
       {

           pDialog.dismiss();
       }


        try{
            apiResult = result;
            System.out.println(apiResult);
            listener.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getCookie(){
        if (!BLHttp.cookies.isEmpty() || !BLHttp.cookies.equals(null)) {
            for (Cookie cookie : BLHttp.cookies) {
                return cookie.getName() + "="  + cookie.getValue() + "; domain=" + cookie.getDomain();
            }
        }
        return "";
    }
}