package com.eipteam.healthsafe.network;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyJSONReq {
    public Request postRequest(String url, JSONObject js) {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(MEDIA_TYPE, js.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        return (request);
    }

    public Request getRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return (request);
    }
}
