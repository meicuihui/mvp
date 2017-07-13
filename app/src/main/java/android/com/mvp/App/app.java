package android.com.mvp.App;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/7/12.
 */

public class app extends Application {
    public static RequestQueue mQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mQueue = Volley.newRequestQueue(this);;
    }
}
