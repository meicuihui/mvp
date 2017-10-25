package android.com.mvp.View;

import android.com.mvp.Interface.MainActivityInterface;
import android.com.mvp.R;
import android.com.mvp.SDK.MyLocationManger;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import android.com.mvp.SDK.MyDatabaseHelper;
import Model.Entity.news;
import Presenter.PresenterImpl.PresenterMainActivity;

public class MainActivity extends AppCompatActivity implements MainActivityInterface,View.OnClickListener{
    Button bt,bt2,bt3,bt11;
    TextView tx;
    ProgressBar progressBar;
    PresenterMainActivity presenterMainActivity;
    MyDatabaseHelper myDatabaseHelper;
    SQLiteDatabase db;
    MyLocationManger myLocationManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bt=(Button)findViewById(R.id.bt);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        bt11=(Button)findViewById(R.id.bt11);
        tx=(TextView)findViewById(R.id.news);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        presenterMainActivity=new PresenterMainActivity(this);
        bt.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt11.setOnClickListener(this);
        myLocationManger=new MyLocationManger(this);
        myDatabaseHelper=new MyDatabaseHelper(this,"infoDatabase.db",null,1);
        db=myDatabaseHelper.getWritableDatabase();
        myLocationManger=new MyLocationManger(this);
        myLocationManger.startTime();
        myLocationManger.setTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myLocationManger.endTime();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void setNews(news news) {
      tx.setText(news.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt:
//                presenterMainActivity.getNews();
//                myLocationManger.getLocation();

                myLocationManger.getLocation();
                myLocationManger.getIphoneInfo();
//                Log.d("tag1",myLocationManger.getIphoneInfo());
                break;
            case R.id.bt2:
                Cursor cursor=db.query("info1",null,null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String id = cursor.getString(cursor.getColumnIndex("location"));
                        String type = cursor.getString(cursor.getColumnIndex("lonlati"));
                        String os = cursor.getString(cursor.getColumnIndex("uselon"));
                        String t = cursor.getString(cursor.getColumnIndex("time"));
//                        Log.d("tag11",location);
//                        Log.d("tag11",time);
                        Log.d("tag1",type+id+os+" "+t);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                Log.d("tag1","bt2");
                break;
            case R.id.bt3:
                ContentValues contentValues1=new ContentValues();
                contentValues1.put("count","100");
                db.update("info",contentValues1,"location=?",new String[]{"成都"});
                break;
            case R.id.bt11:
                db.delete("info","id>?",new String[]{"3"});
                break;
            default:
                break;

        }
    }
}
