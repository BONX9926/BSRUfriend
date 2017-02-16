package com.tumsakka.atiwat.bsrufriend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import static android.R.string.ok;

/**
 * Created by MiniBall on 2/16/2017.
 */

public class GetUser extends AsyncTask<String, Void, String>{

    private Context context;

    public GetUser(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[0]).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("16febV1", "e doin ==>" + e.toString());
            return null;
        }

    }
} //Main Class
