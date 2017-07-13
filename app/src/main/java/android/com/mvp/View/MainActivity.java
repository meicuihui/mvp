package android.com.mvp.View;

import android.com.mvp.Interface.MainActivityInterface;
import android.com.mvp.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import Model.Entity.news;
import Presenter.PresenterImpl.PresenterMainActivity;

public class MainActivity extends AppCompatActivity implements MainActivityInterface,View.OnClickListener{
    Button bt;
    TextView tx;
    ProgressBar progressBar;
    PresenterMainActivity presenterMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt=(Button)findViewById(R.id.bt);
        tx=(TextView)findViewById(R.id.news);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        presenterMainActivity=new PresenterMainActivity(this);
        bt.setOnClickListener(this);
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
                presenterMainActivity.getNews();
                break;
        }
    }
}
