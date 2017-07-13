package Model.ModelImpl;

import android.com.mvp.App.app;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import Model.Entity.news;
import Model.ModelInterface.ModelMainActivityInterface;
import Presenter.PresenterInterface.PresenterListener;

/**
 * Created by Administrator on 2017/7/12.
 */

public class ModelMainActivity implements ModelMainActivityInterface {
    RequestQueue mQueue = app.mQueue;

    @Override
    public void getNews(final PresenterListener presenterListener) {
        String url = "http://gank.io/api/data/Android/10/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET,url, null,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("error").equals("false")) {
                                    JSONArray jsonArray = response.getJSONArray("results");
                                    if (jsonArray == null) {
                                        return;
                                    }
                                    Gson gson = new Gson();
                                    news n = gson.fromJson(jsonArray.getString(0), news.class);
                            }else{
                                presenterListener.onError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   presenterListener.onError();
            }

        });
        mQueue.add(jsonObjectRequest);
    }


}