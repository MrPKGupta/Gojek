package com.example.gojek.ui;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mainView;

    @Inject
    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
    }
}
