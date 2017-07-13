package android.com.mvp.Interface;


import Model.Entity.news;

/**
 * Created by Administrator on 2017/7/12.
 */

public interface MainActivityInterface {
    void showError();
    void showLoading();
    void endLoading();
    void setNews(news news);
}
