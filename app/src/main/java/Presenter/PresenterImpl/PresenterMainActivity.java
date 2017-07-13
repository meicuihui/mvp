package Presenter.PresenterImpl;

import android.com.mvp.Interface.MainActivityInterface;


import Model.Entity.news;
import Model.ModelImpl.ModelMainActivity;
import Presenter.PresenterInterface.PresenterListener;

/**
 * Created by Administrator on 2017/7/12.
 */

public class PresenterMainActivity  implements PresenterListener {
    MainActivityInterface m;

    public PresenterMainActivity(MainActivityInterface m){
           this.m=m;
    }
  public void  getNews(){
         m.showLoading();
         ModelMainActivity m=new ModelMainActivity();
         m.getNews(this);
    }

    @Override
    public void onSuccess(news n) {
        m.setNews(n);
        m.endLoading();
    }


    @Override
    public void onError() {
      m.showError();
    }
}
