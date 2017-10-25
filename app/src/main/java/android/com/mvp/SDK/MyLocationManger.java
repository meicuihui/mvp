package android.com.mvp.SDK;

import android.Manifest;
import android.app.Activity;
import android.com.mvp.App.app;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import Model.Entity.news;

/**
 * Created by Administrator on 2017/8/15.
 */

public class MyLocationManger {
    public LocationManager lm;
    Activity activity;
    Context context;
    long start,end;
    MyDatabaseHelper myDatabaseHelper=null;
    SQLiteDatabase db;
    RequestQueue mQueue = app.mQueue;
    info1 info1;
    public MyLocationManger(Activity a) {
        activity = a;
        lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        context = activity.getApplicationContext();
        myDatabaseHelper= new MyDatabaseHelper(activity,"infoDatabase.db",null,1);
        db=myDatabaseHelper.getWritableDatabase();
        info1=new info1();
    }

    public void getLocation() {
//        Toast.makeText(context, "开始定位!", Toast.LENGTH_SHORT).show();
        String longitude="";
        String latitude="";
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location  location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                Toast.makeText(context,"GPS"+location.getLongitude()+" "+location.getLatitude(),Toast.LENGTH_SHORT).show();
                latitude=location.getLatitude()+"";
                longitude=location.getLongitude()+"";
                info1.setLonlati(location.getLongitude()+","+location.getLatitude());
                getLocationName(longitude,latitude);

            }
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location  location=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null){
                Toast.makeText(context,"net"+location.getLongitude()+" "+location.getLatitude(),Toast.LENGTH_SHORT).show();
                latitude=location.getLatitude()+"";
                longitude=location.getLongitude()+"";
                getLocationName(longitude,latitude);
            }
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }

    }
    public void getLocationName(String lon,String lati){
        String url="http://restapi.amap.com/v3/geocode/regeo?output=json&location="+lon+","+lati+"&key=ee57b33235a997056e2badf2f2ce421f";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET,url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("info").equals("OK")) {
                                String location=response.getJSONObject("regeocode").getString("formatted_address");
                                info1.setLocation(location);
                                Toast.makeText(context,location,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        mQueue.add(jsonObjectRequest);
    }
    public void getIphoneInfo(){
        String imei="";
        String iphone="";
        String operator="";
        TelephonyManager tm = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            imei=tm.getDeviceId();
            iphone=tm.getLine1Number();
            operator=tm.getSimOperatorName();
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},2);
        }
                ContentValues contentValues=new ContentValues();
                contentValues.put("type",android.os.Build.MODEL);
                contentValues.put("os",Build.VERSION.RELEASE);
                contentValues.put("api",Build.VERSION.SDK_INT);
                contentValues.put("os",android.os.Build.HARDWARE);
                contentValues.put("type",Build.CPU_ABI);
                contentValues.put("os",imei);
                contentValues.put("iphone",iphone);
                contentValues.put("os",operator);
                db.insert("info",null,contentValues);

    }
    public void setTime(){
        long now=System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdate=sdf.format(new Date(now));
        info1.setTime(nowdate);
    }
    public  void startTime(){
        start=System.currentTimeMillis();
    }
    public void endTime(){
        end=System.currentTimeMillis();
        long time=end-start;
        long result=time/1000;
        String actName=activity.getLocalClassName();
        info1.setUselon(""+result);
        info1.setActivity(actName);

        ContentValues contentValues=new ContentValues();
        contentValues.put("location",info1.getLocation());
        contentValues.put("lonlati",info1.getLonlati());
        contentValues.put("time",info1.getTime());
        contentValues.put("uselon",info1.getUselon());
        contentValues.put("activity",info1.getActivity());
        db.insert("info1",null,contentValues);
    }

}
