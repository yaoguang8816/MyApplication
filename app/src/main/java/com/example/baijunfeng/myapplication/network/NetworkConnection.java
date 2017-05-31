package com.example.baijunfeng.myapplication.network;

import android.app.Application;
import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.baijunfeng.myapplication.MyApplication;
import com.example.baijunfeng.myapplication.utils.MyJsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by baijunfeng on 17/5/10.
 */

public class NetworkConnection {

    private static NetworkConnection sInstanse;

    public static NetworkConnection getInstance(){
        synchronized (NetworkConnection.class) {
            if(sInstanse == null){
                sInstanse = new NetworkConnection();
            }
            return sInstanse;
        }
    }

    /**
     * 利用Volley获取JSON数据
     */
    public void getJSONByVolley(String url, final Response.Listener<JSONObject> responseListener, final Response.ErrorListener errorListener) {
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getInstance());
//        String JSONDataUrl = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
//        final ProgressDialog progressDialog = ProgressDialog.show(this, "This is title", "...Loading...");

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response="+response);
                        if (response != null) {
                            responseListener.onResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        System.out.println("sorry,Error");
                        if (errorListener != null) {
                            errorListener.onErrorResponse(arg0);
                        }
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
