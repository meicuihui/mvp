package Presenter.PresenterInterface;


import Model.Entity.news;

/**
 * Created by Administrator on 2017/7/12.
 */

public interface PresenterListener {
    void onSuccess(news n);
    void onError();
}
