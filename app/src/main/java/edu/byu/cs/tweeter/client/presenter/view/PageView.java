package edu.byu.cs.tweeter.client.presenter.view;

import java.util.List;

public interface PageView<T> extends BaseView {
    void setLoadingFooter(boolean loading);
    void addItems(List<T> items);
}
