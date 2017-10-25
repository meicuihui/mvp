package android.com.mvp.SDK;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_info="create table info("
            +"id integer primary key autoincrement,"
//            +"location text,"//位置
//            +"count text,"//使用次数
//            +"time text,"//使用时间
            +"os text,"//操作系统版本
            +"type text,"//设备名称
            +"api text,"//sdk版本
            +"cpu text,"//cpu
            +"cpustr text,"//cpu架构
            +"imei text,"//imei
            +"iphone text,"//电话号码
            +"operator text)";//运营商
    public static final String CREATE_info1="create table info1("
            +"id integer primary key autoincrement,"
            +"location text,"//位置
            +"lonlati text,"//经纬度
            +"time text," //使用时间点
            +"uselon text,"//使用时长
            +"activity text)";//使用页面

    public Context mcontext;
    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
      super(context,name,factory,version);
        mcontext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_info);
        db.execSQL(CREATE_info1);
        Toast.makeText(mcontext, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
