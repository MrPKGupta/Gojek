package com.example.gojek;

import android.app.Application;
import android.content.Context;

import com.example.gojek.di.component.AppComponent;
import com.example.gojek.di.component.DaggerAppComponent;
import com.example.gojek.di.module.ApplicationModule;

import timber.log.Timber;

public class GojekApplication extends Application {
    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setUpInjector();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public GojekApplication get(Context context) {
        return (GojekApplication) context.getApplicationContext();
    }

    private void setUpInjector() {
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        appComponent.inject(this);
    }
}
