package com.example.gojek.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.gojek.di.ActivityContext;
import com.example.gojek.ui.MainContract;
import com.example.gojek.ui.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final MainContract.View mainView;
    private Activity mActivity;

    public ActivityModule(Activity activity, MainContract.View mainView) {
        mActivity = activity;
        this.mainView = mainView;
    }

    @Provides
    MainContract.Presenter provideMainPresenter() {
        return new MainPresenter(mainView);
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @ActivityContext
    @Provides
    Context provideContext() {
        return mActivity;
    }
}
